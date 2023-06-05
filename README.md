# gshoes-app

FRONT-END:
+ Basic features done
+ Bugs: add-to-cart button not change to check icon when a product is listed in cart (the solution is I can disable the button when that product is listed on cart and when that product is removed from cart, I can enable it again but I have not figured out yet)
+ The UX and UI are still bad to me because they are not finished yet and I am not good in FE. Sorry for the inconvenience.

GUIDE:

+ Before running this app, you must import database scripts first because this app is not deployed on heroku yet. I am sorry for the inconveniece.
+ Then, you should run BE by your IDE tools. This BE is written by Java.
+ Finally, you can run FE. This FE is written by ReactJS so to run this FE, open your terminal with the absolute directory of this FE and write command line "npm start" to run it. (If react-scripts has not been imported yet, please run command line "npm i react-scripts" firstly and then "npm start")
+ If this application can not run because you found that it is forbidden by CORS policy by inspecting the console (F12 or Ctrl + Shift + I), please change your browser to Google Chrome, enter properties and add "--disable-web-security --disable-gpu --user-data-dir=~/chromeTemp" at the end of its target directory to enable CORS policy.

Thank you and have a good day!
