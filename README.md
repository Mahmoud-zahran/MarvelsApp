**Technologies:**

using the best practices combining the following techs to achieve the goal of the challenge

* Clean Architecture with Repository Pattern and Applied UseCase
* MVVM with Kotlin
* JetPack Compose with material design
* Kotlin Coroutines
* Retrofit
* Coil for loading images
* Navigation compose to navigate between screens using deep linking
* Hilt for Dependency Injection

**App Description**

Using this application, users will be able to browse through the Marvel
library of characters. The data is available by connecting to the Marvel API
(http://developer.marvel.com/).
 
 
You should use the following mockup as a reference for navigating between pages:
https://marvelapp.com/279b309


 
**List of Characters**

In this view, you should display a list of characters loaded from the Marvel API character index. When reaching the end of the list, load and display the next page if there are additional results.


**Character Details**

When selecting a character, show a detailed view of that character. Most information is available from the first API call, except for images in the comics, series, stories, and events sections. These images can be fetched from the resourceURI and should be lazy-loaded. The same behavior is expected when expanding these images.
Note: Please hide any section that hasn't returned data from the API.

**Challenge Objectives**

This challenge will serve as the basis for the technical interview. During the interview, we will discuss your architectural choices, best practices use, and overall understanding of the task. Avoid copying code from online sources. The application should be fully functional.
Finally, the code should be versioned using Git and shared with us through any Git repository hosting service.
We value attention to design. In addition to code quality, we’ll be evaluating how well you replicate the mocked UI. Not all animations and transitions are included in the mockup, so please apply widely recognized animations and transitions as appropriate.




here's the **screens** output of the code 




**Marvel Characters**

<img width="350" alt="MarvelApp 1" src="https://github.com/user-attachments/assets/f7735468-c6b0-4f4e-9f2c-0423e61e8976">

**Marvel Character Details**

<img width="350" alt="MarvelApp 1" src="https://github.com/user-attachments/assets/31e3a748-638c-4a3d-82a6-06fcf74eeaee">


<img width="350" alt="MarvelApp 1" src="https://github.com/user-attachments/assets/f560653e-a00c-447e-93be-6a7ce60d0b8c">

**Marvel Book openned**

<img width="350" alt="MarvelApp 1" src="https://github.com/user-attachments/assets/94e52c69-500e-44b7-87d6-c648341a8363">
<img width="350" alt="MarvelApp 1" src="https://github.com/user-attachments/assets/a578c906-d969-49fb-a5d9-fb8410cdfde1">


