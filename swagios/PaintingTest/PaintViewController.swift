//
//  PaintViewController.swift
//  PaintingTest
//
//  Created by Rodrigo Pélissier on 11-04-15.
//  Copyright (c) 2015 Rodrigo Pélissier. All rights reserved.
//

import UIKit

class PaintViewController: UIViewController {
    
    let paintView = PaintView()
    
    override func loadView() {
        view = paintView
    }
    
    override func prefersStatusBarHidden() -> Bool {
        return true
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

