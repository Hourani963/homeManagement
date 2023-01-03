import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router} from '@angular/router'
import {articleUrl} from '../../apiUrls'
import { FileUploadService } from '../../add-article/file-upload.service';


@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit, OnChanges {
  public ARICLE_URL = articleUrl;

  constructor(private router : Router,private http: HttpClient, private uploadService : FileUploadService){}


  @Input('parentData') public article:any;
  public quantityMessage = '';

  ngOnInit(){
    
  }

  ngOnChanges(){
    
  }
  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/home']);
  }

  async addArticleQuantity(idArt: number){
    this.uploadService.addArticleQuantity(idArt, 1).subscribe(
      async  data => {
        this.quantityMessage = await  data;
        console.log(data)
      }
    )
    //this.reloadComponent() 
  }

  removeArticleQuantity(idArt: number){
    this.uploadService.removeArticleQuantity(idArt, 1).subscribe(
      data => {
        this.quantityMessage = data;
        console.log(data)
      }
    )
    //this.reloadComponent() 
  }
  
}
