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
  
  public allArticleswithCat: any = [];
  public catFilter : boolean = false;
  public redondancePerformersFilter : number = 0;
  public countArticle : number = 0;

  public sortMessage = 'sorted by id';

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
  // true to sort from A to Z
  // false to sort from Z to A
  sortByName(typeSorte : boolean){
    if(typeSorte){
      this.sortMessage = "sorted by name ASC"
      this.allArticles[0].sort(function(a : any,b:any){
        if(a.nom < b.nom) { return -1; }
        if(a.nom > b.nom) { return 1; }
        return 0;
      })
    }
    else{
      this.sortMessage = "sorted by name DESC"
      this.allArticles[0].sort(function(a : any,b:any){
        if(a.nom > b.nom) { return -1; }
        if(a.nom < b.nom) { return 1; }
        return 0;
      })
    }

  }

  getArticleforCats(articleForCats : any){
    this.redondancePerformersFilter = articleForCats.length
    //console.log("redondance = " + this.redondancePerformersFilter)
    //this.allArticles =[]
    console.log(articleForCats)
    //ici j'ai besoin de fair une concatination de tableau parce que dans le html j'utilise que l'objet dans la position 0 -> allArticles[0]
    var newArr : any = []
    if(articleForCats.length != 0){
      this.catFilter = true
      for(let i=0; i<articleForCats.length; i++){
          
          newArr =newArr.concat(articleForCats[i])
          
      }
      this.allArticleswithCat = []
      this.allArticleswithCat.push(newArr)

      this.getArticleJOIN()
      this.deleteRedendance()

      //console.log("%c"+ this.countArticle, "color:green")
      
      this.leadOnePage()


    }else{
      this.catFilter = false;
      this.ngOnInit()
    }
    
  }

  // les articles qui contient au moins un catégorie choisi
  deleteRedendance(){
    for(let i=0; i<this.allArticleswithCat[0].length; i++){
      for(let j=0; j<this.allArticleswithCat[0].length; j++){
        if(i!=j && this.allArticleswithCat[0][i].idArt === this.allArticleswithCat[0][j].idArt){
          this.allArticleswithCat[0].splice(i,1)
          
        }
      }
    }
    this.countArticle = this.allArticleswithCat[0].length
    //console.log("heeeeeeeer " + this.countArticle)
  }
  // les articles qui contiens tous les catégories choisi
  getArticleJOIN(){
    let newArr = [];
    for(let i=0; i<this.allArticleswithCat[0].length; i++){
      var count = 0;
      for(let j=0; j<this.allArticleswithCat[0].length; j++){
        if(this.allArticleswithCat[0][i].idArt === this.allArticleswithCat[0][j].idArt){
          count ++;
          
        }
      }
      if(count == this.redondancePerformersFilter)
        newArr.push(this.allArticleswithCat[0][i])
    }
    this.allArticleswithCat = []
    this.allArticleswithCat.push(newArr)
    // console.log("%c","color:red")
    // console.log(this.allArticleswithCat)
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
    var lastElemnt = this.paginator? ((this.paginator.pageIndex*this.paginator.pageSize) + this.paginator.pageSize) : 20;
    //console.log("%c first Element = "+firstElemnt ,"color:green")
    //console.log("%c first Element = "+lastElemnt ,"color:red")
      var articlesOnePage : any = []
      for(let i=firstElemnt; i<lastElemnt; i++){
        //console.log("%c count article = "+this.countArticle,"color : green")
        if(!this.catFilter){
          if(i<this.countArticle){
            articlesOnePage.push(this.allArticlesFixe[0][i])
          }
          
        }
        else{
          this.countArticle = this.allArticleswithCat[0].length
          if(i<this.allArticleswithCat[0].length){
            articlesOnePage.push(this.allArticleswithCat[0][i])
          }
          
        }
        
      }
      //console.log(articlesOnePage)
      this.allArticles = []

      this.allArticles.push(articlesOnePage)
      //console.log("%c count article = "+this.allArticles[0].length,"color : green")
      
  }



}

