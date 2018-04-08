//
//  ViewController.swift
//  Marshall
//
//  Created by Phartiyal Pankaj on 07/04/18.
//  Copyright © 2018 Phartiyal Pankaj. All rights reserved.
//

import UIKit
import GoogleMaps
import CoreLocation
import Parse
import SwiftyJSON

class ViewController: UIViewController, CLLocationManagerDelegate, GMSMapViewDelegate {
    
    let locationManager = CLLocationManager()
    var marker: GMSMarker!
    var markers: Array<GMSMarker?> = []
    var userLastLocation: PFGeoPoint?
    var incidentId = ""
    
    override func loadView() {
        
        // Ask for Authorisation from the User.
        self.locationManager.requestAlwaysAuthorization()
        
        // For use in foreground
        self.locationManager.requestWhenInUseAuthorization()
        
        let camera = GMSCameraPosition.camera(withLatitude: 0, longitude: 0, zoom: 6.0)
        let mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
        mapView.delegate = self
        view = mapView
        
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        PFGeoPoint.geoPointForCurrentLocation { (geopoint: PFGeoPoint?
            , error: Error?) in
            
            print("location", geopoint!);
            PFUser.current()?.setValue(geopoint!, forKey: "location")
            PFUser.current()?.saveEventually()
            self.userLastLocation = PFGeoPoint(latitude: geopoint!.latitude, longitude: geopoint!.longitude)
            
            let mapView = self.view as! GMSMapView;
            let location = GMSCameraPosition.camera(withLatitude: self.userLastLocation!.latitude, longitude: self.userLastLocation!.longitude, zoom: 12.0)
            mapView.animate(to: location);
            
            if(self.marker != nil) {
                mapView.clear()
            }
            
            self.marker = GMSMarker(position: CLLocationCoordinate2D(latitude: self.userLastLocation!.latitude, longitude: self.userLastLocation!.longitude))
            let marker = self.marker!
            marker.title = "You are here"
            marker.map = mapView
            marker.isFlat = true
            marker.icon = UIImage(named: "current")
            
            let query = PFQuery(className:"Incident")
            
            query.whereKey("location", nearGeoPoint: self.userLastLocation!, withinMiles: 5.0)
            query.findObjectsInBackground { (incidents, error) in
                if error == nil {
                    print("Successfully retrieved \(incidents!.count) scores.")
                    for incident in incidents! {
                        let point = incident.value(forKey: "location") as! PFGeoPoint
                        let locValue = CLLocationCoordinate2D(latitude: point.latitude, longitude: point.longitude);
                        let marker = GMSMarker(position: locValue)
                        marker.title = incident.value(forKey: "category") as! String
                        marker.map = self.view as? GMSMapView
                        marker.isFlat = true
                        marker.appearAnimation = .pop
                        marker.userData = incident.value(forKey: "id") as! String
                    }
                    
                } else {
                    print("Error: \(error!)")
                }
            }
            
            let appDelegate = UIApplication.shared.delegate as! AppDelegate
            appDelegate.socket = appDelegate.manager.defaultSocket
            
            appDelegate.socket.on("notify marshalls") {data, ack in
                do {
                    let dataDict = try data[0] as! NSDictionary
                    let message = try JSON(parseJSON: dataDict.object(forKey: "message")! as! String)
                    let lat = message["location"]["mLatitude"].double!
                    let long = message["location"]["mLongitude"].double!
                    let title = message["categoryId"].string!;
                    print(message);
                    
                    let locValue = CLLocationCoordinate2D(latitude: lat, longitude: long);
                    let marker = GMSMarker(position: locValue)
                    marker.title = title
                    marker.map = self.view as? GMSMapView
                    marker.isFlat = true
                    marker.appearAnimation = .pop
                    marker.userData = message["id"].string!
                } catch {
                    print(error)
                }
                
            }
            appDelegate.socket.connect()
        }
        
        
        
        // Pull incidents
        
    }
    
    func mapView(_ mapView: GMSMapView, didTapInfoWindowOf marker: GMSMarker) {
        self.incidentId = marker.userData as! String
        self.performSegue(withIdentifier: "show_detail", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier! == "show_detail") {
            let vc = segue.destination as! IncidentDetailViewController
            vc.incidentId = self.incidentId
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

