import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router} from '@angular/router'
import {articleUrl} from '../../apiUrls'
import { FileUploadService } from '../../add-article/file-upload.service';
import {CatergoriesService} from '../cat-menu/catergories.service'

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit, OnChanges {
  public ARICLE_URL = articleUrl;

  constructor(private router : Router,private http: HttpClient, private uploadService : FileUploadService, private catService : CatergoriesService){}

  public catsForArt : any = [];
  public allCats : any = [];


  @Input('parentData') public article:any;
  public quantityMessage = '';

  ngOnInit(){
    this.getAllCatForArt()
    this.quantityMessage = this.article.quantity
  }

  ngOnChanges(){
    this.getAllCatForArt()
  }
  goToProfile(idArt : number){
    this.router.navigate(["/profile", idArt])
  }
  removeCatFromArt(idCat : number, idPerformer : number){
    this.uploadService.removeCatFromArt(idCat, idPerformer).subscribe(
      data =>{
        //console.log(data)
        this.catsForArt = []
        this.catsForArt.push(data)
        
      }
    )
    
  }
  getAllCats(){
    //console.log(this.allCats)
    this.allCats = this.catService.getCats()
  }
  addCatToArticle(idCat : number, idPerformer : number){
    //console.log(idCat)
    this.uploadService.getCat(idCat, idPerformer).subscribe(
      data =>{
        //console.log(data)
        this.catsForArt = []
        this.catsForArt.push(data)
      }
    )
  }
  getAllCatForArt(){
      this.uploadService.getCatForArt(this.article.idArt).subscribe(
        (data : any) =>{
          this.catsForArt.push(data)
          console.log(this.catsForArt)
          return data[0]
        }
      )
      
  }

  addArticleQuantity(idArt: number){
    this.uploadService.addArticleQuantity(idArt, 1).subscribe(
      data => {
        this.quantityMessage =  data;
        console.log(data)
      }
    )

    
  }

  removeArticleQuantity(idArt: number){
    this.uploadService.removeArticleQuantity(idArt, 1).subscribe(
      data => {
        this.quantityMessage = data;
        console.log(data)
      }
    )
    
  }
  
}
