# Zip Directory

I needed an easy way to zip a Windows directory.  So, I created this simple process using a `ZipOutputStream`.

Here's the output from one of my test runs.

    Creating zip file: D:\Eclipse\FS22_BalanceSheet\FS22_BalanceSheet.zip
    Writing BalanceSheet.lua to zip file
    Writing gui to zip file
    Writing gui/guiProfiles.xml to zip file
    Writing gui/InGameMenuBalanceSheet.lua to zip file
    Writing gui/inGameMenuBalanceSheet.xml to zip file
    Writing images to zip file
    Writing images/BalanceSheet.png to zip file
    Writing images/menuIcon.dds to zip file
    Writing languages to zip file
    Writing languages/l10n_en.xml to zip file
    Writing modDesc.xml to zip file

The process writes the zip file in the input Windows directory.  Zip files are automatically excluded from the zip process.

There is only one required argument.

- The file path to the directory.

The remaining arguments are extension exclusions.  You can have as many extension exclusions as you want.  All other directories and files are included.  Here's an example execute command, taken from one of my Windows batch files.

    java -jar D:\Eclipse\Eclipse-2024-workspace\com.ggl.zipdirectory\zipdirectory.jar D:\Eclipse\FS22_BalanceSheet bat

I also exclude `.bat` files.  The process adds the dot prefix.
