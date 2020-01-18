module Main where

import Prelude hiding ( Maybe, Nothing, Just,
                        Either, Left, Right
                    )
import qualified Data.Char as Ch
import Debug.Trace (trace)
import Data.Bifunctor
                    

main :: IO ()
main = putStrLn "Hello, Haskell!"

stringToUpper:: String -> String
stringToUpper s = fmap Ch.toUpper s

stringToLower:: String -> String
stringToLower s = fmap Ch.toLower s

data Maybe a = Nothing
            | Just a
            deriving (Show, Eq)

safeHead:: [a] -> Maybe a
safeHead [] = Nothing
safeHead (x:_) = Just x

debug:: Show a => a -> Maybe String -> a
debug value message = trace (fromMaybe "" message ++ show value) value

fromMaybe:: a -> Maybe a -> a
fromMaybe x Nothing = x
fromMaybe _ (Just x) = x

instance Functor Maybe where
    fmap f Nothing  = Nothing
    fmap f (Just x) = Just (f x)

makeBold :: String -> String
makeBold s = "<b>" ++ s ++ "</b>"

currentUserName :: Maybe String
currentUserName = Just "John Doe"

formattedUserName :: Maybe String -> Maybe String
formattedUserName username = 
    fmap makeBold $ fmap stringToUpper username

formattedUserName' :: Maybe String -> Maybe String
formattedUserName' = (fmap makeBold) . (fmap stringToUpper)

data Either a b = Left a 
                | Right b
                deriving (Show, Eq)

type StrErr a = Either String a

safeHeadVerbose :: [a] -> StrErr a
safeHeadVerbose [] = Left "The list is empty"
safeHeadVerbose (x:_) = Right x

maybeToErr :: Maybe a -> StrErr a
maybeToErr Nothing = Left "Unknown error"
maybeToErr (Just x) = Right x

errToMaybe :: StrErr a -> Maybe a 
errToMaybe (Left _) = Nothing
errToMaybe (Right x) = Just x

instance Functor (Either a) where
    fmap f (Right x) = Right (f x)
    fmap f (Left e) = Left e

currentUserNameVerbose :: StrErr String
currentUserNameVerbose = Right "Jane Doe"

formattedUserNameVerbose :: StrErr String -> StrErr String
formattedUserNameVerbose username = 
    fmap makeBold $ fmap stringToUpper username


formattedUserNameUniversal :: Functor f => f String -> f String
formattedUserNameUniversal username = 
    fmap makeBold $ fmap stringToUpper username

instance Bifunctor Either where
    bimap f g (Left x) = Left (f x)
    bimap f g (Right y) = Right (g y)

    first f (Left x) = Left (f x)
    first f (Right y) = Right y

    second g (Left x) = Left x
    second g (Right y) = Right (g y)