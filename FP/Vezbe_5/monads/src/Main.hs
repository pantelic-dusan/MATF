module Main where

import Prelude hiding ( Maybe, Nothing , Just
                      , Either, Left, Right)

import qualified Data.Char as Ch 
import Debug.Trace (trace)
import Data.Bifunctor

main :: IO ()
main = putStrLn "Hello, Haskell!"

data Maybe a = Nothing
              | Just a
              deriving (Show, Eq)

instance Functor Maybe where
    fmap f Nothing = Nothing
    fmap f (Just x) = Just (f x)

instance Applicative Maybe where
    pure x = Just x
    (<*>) Nothing _ = Nothing
    (<*>) _ Nothing = Nothing
    (<*>) (Just f) (Just x) = Just (f x)

stringToUpper :: String -> Maybe String
stringToUpper s = Just $ fmap Ch.toUpper s

stringToLower :: String -> Maybe String
stringToLower s = Just $ Ch.toLower <$> s

makeBold :: String -> Maybe String
makeBold s = Just $ "<b>" ++ s ++ "</b>"

instance Monad Maybe where
    return x = Just x
    Nothing >>= _ = Nothing
    Just x >>= f = f x

type Birds = Int 
type Pole = (Birds, Birds)

landLeft' :: Birds -> Pole -> Pole
landLeft' n (left, right) = (left + n, right)

landRight' :: Birds -> Pole -> Pole
landRight' n (left, right) = (left, right + n)

x -: f = f x

landLeft :: Birds -> Pole -> Maybe Pole
landLeft n (left, right)
    | abs((left + n) - right) < 4 = Just (left + n, right)
    | otherwise = Nothing

landRight :: Birds -> Pole -> Maybe Pole
landRight n (left, right)
    | abs (left - (right + n)) < 4 = Just (left, right + n)
    | otherwise = Nothing

