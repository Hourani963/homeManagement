import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {articleUrl, categoryUrl} from '../apiUrls'


@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

    
  constructor(private http:HttpClient) { }
  
  // Returns an observable
  uploadFile(file : any, idArticle : number):Observable<any> {
  
      // Create form data
      const formData = new FormData(); 
        
      // Store form name as "file" with file data
      formData.append("file", file, file.name);
        
      // Make http post request over api
      // with formData as req
      return this.http.post(articleUrl+"/"+idArticle+"/image/upload", formData,{
      responseType : 'text'})
  }
  uploadArticle(nom : string, quantity : number,description:string):Observable<any> {


    let article ={"nom":nom, "quantity": quantity,"description":description}
 
    console.log(article)

    return this.http.post(articleUrl+"/addArticle",article,{
    responseType : 'text'})
  }

  uploadCat(idCat:number, idArt:number):Observable<any> {
    
    return this.http.get(categoryUrl+"/"+idCat+"/addCatToArt/"+idArt,{
    responseType : 'text'})
  }
}
