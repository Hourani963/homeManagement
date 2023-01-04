import { Injectable } from '@angular/core';



@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  private allArticle : any = [];

  constructor() { }


  setArticels(articles : []){
    this.allArticle.push(articles);
  }


  getArticles(){
    return this.allArticle;
  }

}
