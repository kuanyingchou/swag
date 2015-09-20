//
//  PaintView.swift
//  PaintingTest
//
//  Created by Rodrigo Pélissier on 11-04-15.
//  Copyright (c) 2015 Rodrigo Pélissier. All rights reserved.
//

import UIKit

class PaintView: UIView {
    
    // Image to cache previous paths
    var drawImage: UIImage?
    
    // All touch points
    private var samplePoints = [CGPoint]()
    
    // Path to draw
    private let path = UIBezierPath()
    
    // Whether to clear current image
    var shouldClear = false
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        backgroundColor = .whiteColor()
        
        path.lineCapStyle = .Round
        path.lineJoinStyle = .Round
        path.lineWidth = 3
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        let touch = touches.first!
        let loc = touch.locationInView(self)
        samplePoints.append(loc)
    }
    
    override func touchesMoved(touches: Set<UITouch>, withEvent event: UIEvent?) {
        let touch = touches.first!

        for coalescedTouch in event!.coalescedTouchesForTouch(touch)! {
            samplePoints.append(coalescedTouch.locationInView(self))
        }
        
        setNeedsDisplay()
    }
    
    // Take a snapshot of the current view and store it in drawImage before emptying the array
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        UIGraphicsBeginImageContextWithOptions(bounds.size, false, 0)
        
        drawViewHierarchyInRect(bounds, afterScreenUpdates: true)
        drawImage = UIGraphicsGetImageFromCurrentImageContext()
        
        UIGraphicsEndImageContext()
        
        samplePoints.removeAll()
    }
    
    override func touchesCancelled(touches: Set<UITouch>?, withEvent event: UIEvent?) {
        touchesEnded(touches!, withEvent: event)
    }
    
    override func motionEnded(motion: UIEventSubtype, withEvent event: UIEvent?) {
        if motion == .MotionShake {
            shouldClear = true
            drawImage = nil
            setNeedsDisplay()
        }
    }
    
    func getMidPointFromA(a: CGPoint, andB b: CGPoint) -> CGPoint {
        return CGPoint(x: (a.x + b.x) / 2, y: (a.y + b.y) / 2)
    }
    
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
        guard shouldClear != true else {
            UIColor.whiteColor().setFill()
            UIRectFill(rect)
            shouldClear = false
            return
        }
        
        let ctx = UIGraphicsGetCurrentContext()

        CGContextSetAllowsAntialiasing(ctx, true)
        CGContextSetShouldAntialias(ctx, true)
        
        UIColor.blackColor().setStroke()
        
        path.removeAllPoints()
                
        drawImage?.drawInRect(rect)
        
        if !samplePoints.isEmpty {
            path.moveToPoint(samplePoints.first!)
            path.addLineToPoint(getMidPointFromA(samplePoints.first!, andB: samplePoints[1]))
            
            // Iterate through the remaining touch points except for the last one
            for idx in 1..<samplePoints.count - 1 {
                let midPoint = getMidPointFromA(samplePoints[idx], andB: samplePoints[idx + 1])
                path.addQuadCurveToPoint(midPoint, controlPoint: samplePoints[idx])
            }
            
            path.addLineToPoint(samplePoints.last!)
            
            path.stroke()
        }
    }

}
