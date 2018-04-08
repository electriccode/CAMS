//
//  AuthenticationViewController.swift
//  Marshall
//
//  Created by Phartiyal Pankaj on 07/04/18.
//  Copyright © 2018 Phartiyal Pankaj. All rights reserved.
//

import UIKit
import Parse

class AuthenticationViewController: UIViewController, PFLogInViewControllerDelegate, PFSignUpViewControllerDelegate {
    
    var signupController: PFSignUpViewController!;
    var loginController: PFLogInViewController!;
    var user: PFUser!;

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        let statWindow = UIApplication.shared.value(forKey:"statusBarWindow") as! UIView
        let statusBar = statWindow.subviews[0] as UIView
        statusBar.backgroundColor = UIColor.white.withAlphaComponent(0.45)
        
//        do {
//            try self.user = PFUser.logIn(withUsername: "tushar", password: "tushar_chegg@123")
//        } catch {
//            print("Error info: \(error)")
//        }
        
        
        if(self.user == nil) {
            self.signupController = PFSignUpViewController()
            self.loginController = PFLogInViewController()
            self.signupController.delegate = self
            self.loginController.delegate = self
            self.loginController.signUpController = self.signupController
            self.signupController.fields = [.usernameAndPassword,
                .email,
                .signUpButton,
                .additional,
                .dismissButton]
            let signupView = self.signupController.view as! PFSignUpView
            signupView.additionalField!.placeholder = "Phone"
            self.present(self.loginController, animated:true, completion: nil)
        } else {
            self.performSegue(withIdentifier: "authenticated", sender: self)
        }
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func signUpViewController(_ signUpController: PFSignUpViewController, didSignUp user: PFUser) {
        self.user = user;
        user.setObject(user.value(forKey: "additional")!, forKey: "phone")
        user.setObject("marshall", forKey: "role")
        user.saveEventually()
        self.dismiss(animated: true, completion: nil)
    }
    
    func log(_ logInController: PFLogInViewController, didLogIn user: PFUser) {
        self.user = user;
        self.dismiss(animated: true, completion: nil)
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "open_url" {
            let vc = segue.destination as! IncidentDetailViewController
            let appDelegate = UIApplication.shared.delegate as! AppDelegate
            vc.incidentId = appDelegate.incidentId
        }
        
    }
    

}
