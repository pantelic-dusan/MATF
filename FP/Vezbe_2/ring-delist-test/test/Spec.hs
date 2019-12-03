import Lib

import Data.Function (on)

import Test.QuickCheck

main :: IO ()
main = putStrLn "Test suite not yet implemented"

prop_conversations s = 
    s == toList (fromList s)
    where types = s :: [Int]

prop_focusNext_focusPrev s =
    s == toList $ focusPrev $ focusNext $ fromList s
    where types = s :: [Int]

prop_focusPrev_focusNext s =
        s == toList $ focusNext $ focusPrev $ fromList s
        where types = s :: [Int]