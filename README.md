# Captch-up
ESGI's Android Reactive Programming course's project

### Objective
Creation of an Android App with some specifications :
- Local and remote data persistence 
- Using fragments
- With at least one App Widget
- Using at least one Google API

### Application
This android app contains a game thas uses the Cloud Vision API : the player upload any picture from his gallery and after a call to the Vision API a level is created with the 3 most relevant predictions (score > 80%). Then the player can try to guess them :) </br></br> 
<img src="/app/src/main/screenshot1.jpg" width=280>
<img src="/app/src/main/screenshot2.jpg" width=280>
<img src="/app/src/main/screenshot3.jpg" width=280>

## Specs
- User are authentified with [Firebase Auth](https://firebase.google.com/docs/auth/) 
- Level data is stored in [Firebase Realtime Database](https://firebase.google.com/docs/database/) and images are stored in [Cloud Storage for Firebase](https://firebase.google.com/docs/storage/) 
- Images are analyzed with the [Cloud Vision API](https://cloud.google.com/vision/)
- Images are cached to improve performance
- Predictions are translated in French with [Cloud Translation API](https://cloud.google.com/translate/docs/)

## Additional libraries
- [Picasso](https://github.com/square/picasso) : Easy loading of images
- [Jinatonic.Confetti](https://github.com/jinatonic/confetti) : To display confetti when the user wins a level. SO important.

## Authors
- [Célia Casagrande](https://github.com/csgrdcelia) - Idea and app dev 
