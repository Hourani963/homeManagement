import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {articleUrl, categoryUrl} from '../apiUrls'
import {BehaviorSubject, Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class DataService {



  constructor(private http:HttpClient) { }



  getPhotoList(idArt : number){
    return this.http.get(articleUrl+"/"+idArt+"/image/downloadAll")
  }


  getVideoList(idArt : number){
    return this.http.get(articleUrl+"/"+idArt+"/video/downloadAll")
  }

  // isArtProfilePhoto doit être false ou true
  addPhotoToPerformer(idArt : number, file : File, isArtProfilePhoto:string){
    const formData = new FormData(); 
        
    // Store form name as "file" with file data
    formData.append("file", file, file.name);
    formData.append("isArtProfilePhoto",isArtProfilePhoto);
    // Make http post request over api
    // with formData as req
    return this.http.post(articleUrl+"/"+idArt+"/image/upload", formData,{
    responseType : 'text'})
  }
  addVideoToPerformer(idArt : number, file : File){
    const formData = new FormData(); 
        
    formData.append("file", file, file.name);

    return this.http.post(articleUrl+"/"+idArt+"/video/upload", formData,{
    responseType : 'text'})
  }

  getPerformerById(idArt : number){
    return this.http.get(articleUrl+"/"+idArt)
  }

  deleteVideo(nomPerformer : string, nomVid : string){
    return this.http.delete(articleUrl+"/"+nomPerformer+"/deleteVideo/"+nomVid,{
      responseType : 'text'})
  }
  deleteImage(nomPerformer : string, nomImage : string){
    return this.http.delete(articleUrl+"/"+nomPerformer+"/deleteImage/"+nomImage,{
      responseType : 'text'})
  }

}
