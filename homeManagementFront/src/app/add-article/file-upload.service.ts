import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {articleUrl, categoryUrl} from '../apiUrls'


@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

    
  constructor(private http:HttpClient) { }
  
  // Sent file to server
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
  // add new article
  uploadArticle(nom : string, quantity : number,description:string):Observable<any> {


    let article ={"nom":nom, "quantity": quantity,"description":description}
 
    console.log(article)

    return this.http.post(articleUrl+"/addArticle",article,{
    responseType : 'text'})
  }

  // add Cat to article
  getCat(idCat:number, idArt:number):Observable<any> {
    
    return this.http.get(categoryUrl+"/"+idCat+"/addCatToArt/"+idArt,{
    responseType : 'text'})
  }
  // get all article for cat
  getArtForCat(idCat:number){
    return this.http.get(categoryUrl+"/"+idCat+"/getAllArticles")
  }
  // get all cat for article
  getCatForArt(idArt:number){
    return this.http.get(articleUrl+"/"+idArt+"/getAllCat")
  }
  //
  // add new cat
  uploadCat(nomCat:string){
    let cat ={"nomCat":nomCat}
 
    console.log(cat)

    return this.http.post(categoryUrl+"/addCat",cat,{
    responseType : 'text'})
  }

  addArticleQuantity(idArticle:number, quantity : number){
    let article ={"quantity": quantity}
    return this.http.post(articleUrl+"/addQuantityArticle/"+idArticle,article,{
      responseType : 'text'})
  }

  removeArticleQuantity(idArticle:number, quantity : number){
    let article ={"quantity": quantity}
    return this.http.post(articleUrl+"/removeQuantityArticle/"+idArticle,article,{
      responseType : 'text'})
  }
}
