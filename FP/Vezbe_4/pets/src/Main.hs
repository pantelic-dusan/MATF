module Main where

main :: IO ()
main = putStrLn "Hello, Haskell!"

data Pet = MkPet { regId :: Int
                 , name :: String
                 , age :: Int
                 , height :: Float
                 , weight :: Float
                 } deriving (Show)

yourPet = MkPet 1 "pera" 5 10.1 50.2

myPet = MkPet { regId = 1, name = "Pera", age = 5, height = 10.1, weight = 50.2 }

myPetOlder = myPet {age = 6}
