import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'
import { FileUploadService } from '../add-article/file-upload.service';
import { HttpClient } from '@angular/common/http';
import {articleUrl} from '../apiUrls'

export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{

  public allArticles: any = [];
  public articleWithCats: any = [];
  constructor(private router : Router,private http: HttpClient){}

  ngOnInit(){
    this.http.get<any>(articleUrl).subscribe(data => {
      this.allArticles.push(data)
      //console.log(this.allArticles)
})
} 
  getArticleforCats(articleForCats : any){
    this.allArticles =[]
 
    //ici j'ai besoin de fair une concatination de tableau parce que dans le html j'utilise que l'objet dans la position 0 -> allArticles[0]
    var newArr : any = []
    if(articleForCats.length != 0){
      
      for(let i=0; i<articleForCats.length; i++){
      
          newArr =newArr.concat(articleForCats[i])
      }
      this.allArticles.push(newArr)
      console.log(this.allArticles )
    }else{
      this.ngOnInit()
    }
    
  }

  // navigation general controled by the html
  onClick(endPoint:string){
    this.router.navigate([endPoint])
  }

  
  tiles: Tile[] = [
    {text: 'buttons', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'category', cols: 3, rows: 1, color: '#DDBDF1'},
    {text: 'articles', cols: 4, rows: 24, color: '#DDBDF1'},
  ];



}

