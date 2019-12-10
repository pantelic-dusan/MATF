import Data.Char

mySum :: Num a => [a] -> a
mySum [] = 0
mySum (x:xs) = x + mySum xs

mySum' :: Num a => [a] -> a
mySum' xs =
    let mySumHelper acc values =
                    case values of
                         [] -> acc
                         (x:xs) -> mySumHelper (acc + x) xs
    in mySumHelper 0 xs

mySum'' xs =
    foldl (+) 0 xs

myFilter:: (a -> Bool) -> [a] -> [a]
myFilter _ [] = []
myFilter p (x:xs) = let filteredTail = myFilter p xs
                    in if p x then x: filteredTail
                                else filteredTail

myFilter' :: (a -> Bool) -> [a] -> [a]
myFilter' _ [] = []
myFilter' p xs =
    let myFilterHelper acc values =
                        case values of
                            [] -> acc
                            (x:xs) -> myFilterHelper (if p x then x:acc else acc) xs
    in myFilterHelper [] (reverse xs)

myFilter'' :: (a -> Bool) -> [a] -> [a]
myFilter'' p xs =
    foldr (\ x acc -> if p x then x:acc else acc) [] xs

myConcat :: [a] -> [a] -> [a]
myConcat ls rs =
    foldr (:) rs ls

takeWord' :: String -> String
takeWord' sentence = takeWhile (not . isSpace) sentence

takeWord'' :: String -> String
takeWord'' sentence = takeWhile (not . isSpace) (dropWhile isSpace sentence)

takeWord :: String -> String
takeWord = takeWhile (not . isSpace) . dropWhile isSpace

takeWordVerbose :: String -> String
takeWordVerbose = 
    let isNotSpace = not . isSpace
    in takeWhile isNotSpace . dropWhile isSpace

breakWord :: String -> (String, String)
breakWord = break isSpace . dropWhile isSpace

breakWord2 :: String -> (String, String, String)
breakWord2 sentence = 
    let (word1, rest1) = breakWord sentence
        (word2, rest2) = breakWord rest1
    in (word1, word2, rest2)

filterByIndex p xs =
    let withIndices xs = zip [1..] xs
        indexed        = withIndices xs
        paired         = p . fst
    in map snd $ filter paired indexed

applyFunctions' x fns = map (\ f -> f x) fns
applyFunctions x fns = map ($x) fns
applyFunctionsRF = map . flip id

main :: IO ()
main = putStrLn "Hello"