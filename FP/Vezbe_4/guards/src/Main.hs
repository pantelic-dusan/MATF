module Main where

main :: IO ()
main = do
    putStrLn "Hello, Haskell!"

accumulate :: (Eq a, Num a) => (a->a->a) -> [a] -> a
accumulate f xs
        | xs == [] = 0
        | otherwise = f (head xs) (accumulate f $ tail xs)

accumulate' :: (Eq a, Num a) => (a -> a -> a) -> [a] -> a
accumulate' f xs
        | xs == [] = 0
        | (x:[]) <- xs = x
        | (x:xs) <- xs = f x (accumulate' f xs)
