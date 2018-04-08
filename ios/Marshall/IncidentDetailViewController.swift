//
//  IncidentDetailViewController.swift
//  Marshall
//
//  Created by Phartiyal Pankaj on 08/04/18.
//  Copyright © 2018 Phartiyal Pankaj. All rights reserved.
//

import UIKit
import NVActivityIndicatorView
import Parse

class IncidentDetailViewController: UIViewController, NVActivityIndicatorViewable {
    
    @IBOutlet weak var doneButton: UIButton!
    @IBOutlet weak var incidentDetails: UILabel!
    @IBOutlet weak var incidentCategory: UILabel!
    @IBOutlet weak var incidentImage: UIImageView!
    var incidentId = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        if appDelegate.incidentId != "" {
            self.incidentId = appDelegate.incidentId
        }
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.incidentDetails.text = ""
        self.incidentCategory.text = ""
        self.doneButton.backgroundColor = UIColor.white.withAlphaComponent(0.45)
        self.doneButton.layer.cornerRadius = 10
        self.startAnimating()
        
        
        let query = PFQuery(className:"Incident")
        query.whereKey("id", equalTo: self.incidentId)
        query.findObjectsInBackground { (objects, error) in
            if error == nil && objects?.count == 1 {
                let incident = objects!.first
                self.incidentDetails.text = incident?.object(forKey: "description") as? String
                self.incidentCategory.text = incident?.object(forKey: "category") as? String
                if let userPicture = incident!.object(forKey: "photo") as? PFFile {
                    userPicture.getDataInBackground(block: { (imageData: Data?, error) in
                        if (error == nil) {
                            let image = UIImage(data:imageData!)
                            self.incidentImage.image = image
                            self.stopAnimating()
                        }
                    })
                }
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
