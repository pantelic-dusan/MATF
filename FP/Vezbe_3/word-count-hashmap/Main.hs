import System.Environment

import qualified Data.List as List
import qualified Data.Char as Char
import qualified Data.HashMap.Strict as HashMap

import Data.Tuple

main :: IO ()
main = do
    text <- getContents

    (arg: _) <- getArgs

    let n = read arg :: Int

    putStrLn $ process n text

process :: Int -> String -> String
process n text =
    let
        ws = words $ map Char.toLower $
                    map (\ c -> if Char.isLetter c then c else ' ') $ text

        word_occs = HashMap.toList $
                    HashMap.fromListWith (+) [(w,1) | w <- ws]
        
        sorted_by_occs = List.sortBy (flip compare) $ map swap $ word_occs

    in unlines $ map show $ take n $ sorted_by_occs

