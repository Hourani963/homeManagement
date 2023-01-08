import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {articleUrl, categoryUrl} from '../../apiUrls'


@Injectable({
  providedIn: 'root'
})
export class CatergoriesService {


  private categories : any = []

  constructor(private http:HttpClient) { }

  setCats(cats : any){
    this.categories.push(cats)
  }

  getCats(){
    return this.categories;
  }


  // AddCatToArt function is present in fileUploadService with (getCat) name

}
