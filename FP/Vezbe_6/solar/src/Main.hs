module Main where

import Graphics.Gloss
import Graphics.Gloss.Juicy

windowSize = 800

window :: Display
window = InWindow "Solar" (800, 800) (10, 10)

background :: Color
background = black

scaleEq factor = scale factor factor

sunPicture   = scaleEq (0.20 / 256.0) $ loadJuicy "images/sun.png"
earthPicture = scaleEq (0.05 / 256.0) $ loadJuicy "images/earth.png"
moonPicture  = scaleEq (0.03 / 256.0) $ loadJuicy "images/moon.png"

view :: Float -> Picture
view time = scale (windowSize / 2) (windowSize / 2) $
     let hours           = 100 * time
         sunRotation     = 360.0 * hours / (15.0 * 24.0)

         earthRotation   = 360.0 * hours / (1.0 * 24.0)
         earthRevolution = 360.0 * hours / (365.0 * 24.0)

         moonRotation    = 360.0 * hours / (28.0 * 24.0)
         moonRevolution  = 360.0 * hours / (28.0 * 24.0)

     in pictures
              [ rotate sunRotation $ sunPicture
              , rotate earthRevolution $ translate 0.5 0 $
                    pictures [ rotate earthRotation earthPicture
                             , rotate moonRevolution $
                                     translate 0.1 0 $
                                 rotate moonRotation $ moonPicture
                             ]
              ]

main :: IO ()
main = animate window background view
