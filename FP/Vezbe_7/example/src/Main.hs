module Main where

main :: IO ()
main = putStrLn "Hello, Haskell!"

countGreater :: Ord a => [a] -> Int
countGreater [] = 0
countGreater (x:xs) = countGreaterPom x xs
    where countGreaterPom x xs
            | null xs = 0
            | (head xs) > x = 1 + countGreaterPom x (tail xs)
            | otherwise = 0 + countGreaterPom x (tail xs)

countGreater' :: Ord a => [a] -> Int
countGreater' [] = 0
countGreater' (x:xs) = foldl (\acc e -> if e > x then acc + 1 else acc) 0 xs

countGreater'' :: Ord a => [a] -> Int
countGreater'' [] = 0
countGreater'' (x:xs) = length $ filter (>x) xs

prop_test :: Ord a => [a] -> Bool
prop_test l = (countGreater l) == (countGreater' l) 

checkString :: String -> Bool
checkString s = any (\tup -> fst tup == snd tup) $ zip s (tail s)

data Model = Waiting
            | Either String String

whichSort :: Ord a => [a] -> String
whichSort l
    | null l = "EQ"
    | ifAll (==) l = "EQ"
    | ifAll (>=) l = "GT"
    | ifAll (<=) l = "LT"
    | otherwise = "NS"
    where ifAll f l = all (\t -> f (fst t) (snd t)) $ zip l (tail l)

firstLessThen :: Ord a => a -> [a] -> Maybe Int
firstLessThen x l = 
    if null greater then Nothing
                    else Just (snd $ head greater)
    where 
        greater = getGreaterElements x l
        getGreaterElements x l = filter (\t -> fst t < x) $ zip l [0..]