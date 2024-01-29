# ADVGFXCipher

@author Anastasiia Ryzhkova
@version Java 17

# Description

The application provides a user-friendly interface for encrypting and decrypting text files using the ADFGVX cipher. Users can specify input and output directories, set an encryption key, and process multiple text files in bulk.

# To Run

Application is compiled from console in bin directory:
jar -cf ADGVXCipher.jar *
Application is run from console at .jar file directory:
java -cp .:./ADGVXCipher.jar ie.atu.sw.Runner
Then navigate through console options (1 and 2) to set desired input and output folders and set the key with option 3, select desired operation (4 for encryption, 5 for decryption).

# Features

```
● Specify the input and output directories with system path 
    ○ Path shall follow valid  format for filesystem (e.g. /Users/tehnoezh/Documents/OOP/FinalProject/textfiles)
    ○ If output directory is not present in filesystem it will be created
    ○ If path contains leading or trailing spaces they will be trimmed
● Set key
    ○ Path shall follow valid  format for filesystem (e.g. /Users/tehnoezh/Documents/OOP/FinalProject/textfiles)
    ○ Both uppercase or mixed case key is valid (e.g. “CODE”, “Code”)
● Encrypt
    ○ All .txt files in input directory will be encrypted using ADFGVX Cipher encryption algorithm
    ○ First columnar transposition for encryption is performed 
    ○ Then columns are rearranged based on the keyword alphabet order
    ○ Final ciphertext is made by reading the letters of the table by columns starting from top to bottom and from left to right
● Decrypt
    ○ All .txt files in input directory will be decrypted using ADFGVX Cipher encryption algorithm
    ○ Key used for decryption shall match the key which has been used for encryption
    ○ The ciphertext is rearranged based on permutation from the key 
    ○ Then the appropriate letter is found in the keySquare using two letters as coordinates
    ○ The decrypted text is provided as uppercase plaintext without whitespaces

```

