import { Component, OnInit, AfterViewInit,ViewChild } from '@angular/core';
import {Router} from '@angular/router'
import { FileUploadService } from '../add-article/file-upload.service';
import  {ArticlesService} from '../articles.service'
import { HttpClient } from '@angular/common/http';
import {articleUrl} from '../apiUrls'
import {MatPaginator} from'@angular/material/paginator'
import { first, tap } from 'rxjs';
import {MatTableDataSource}from '@angular/material/table'


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
export class HomeComponent implements OnInit ,AfterViewInit{

  // allArticles used to show
  public allArticles: any = [];
  // used to get raw data from back
  public allArticlesFixe: any = [];
  
  public countArticle : number = 0;

  constructor(private router : Router,private http: HttpClient, private articleService : ArticlesService){}
  

  dataSource = new MatTableDataSource(this.allArticlesFixe)
  @ViewChild('paginator') paginator : MatPaginator

  ngOnInit(){
    this.dataSource.paginator = this.paginator
    this.http.get<any>(articleUrl).subscribe(data => {
      this.allArticlesFixe.push(data)
      this.countArticle = data.length
      //this.allArticles.push(data)
      //console.log(this.allArticlesFixe)
      this.leadOnePage();
      // to share data with search bar
      this.articleService.setArticels(this.allArticlesFixe[0]);
     })
     
  }
  
  ngAfterViewInit(): void {
    this.paginator.page.pipe(
      tap(()=>this.leadOnePage())
    ).subscribe();
    
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
      this.countArticle = this.allArticles[0].length
      this.leadOnePage()
      //console.log(this.allArticles )
    }else{
      this.ngOnInit()
    }
    
  }

  // navigation general controled by the html
  onClick(endPoint:string){
    this.router.navigate([endPoint])
  }


//lightpink
// #DDBDF1
  tiles: Tile[] = [
    {text: 'buttons', cols: 1, rows: 1, color: ''},
    {text: 'category', cols: 3, rows: 1, color: ''},
    
  ];

  leadOnePage(){

    var firstElemnt = this.paginator? this.paginator.pageIndex*this.paginator.pageSize:0;
    var lastElemnt = this.paginator? ((this.paginator.pageIndex*this.paginator.pageSize) + this.paginator.pageSize) : 5;
    console.log("%c first Element = "+firstElemnt ,"color:green")
    console.log("%c first Element = "+lastElemnt ,"color:red")
      var articlesOnePage : any = []
      for(let i=firstElemnt; i<lastElemnt; i++){
        if(i<this.countArticle)
          articlesOnePage.push(this.allArticlesFixe[0][i])
      }
      //console.log(articlesOnePage)
      this.allArticles = []
      this.allArticles.push(articlesOnePage)
      
  }



}

