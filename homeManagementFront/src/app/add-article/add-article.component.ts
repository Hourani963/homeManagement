import { Component, OnInit } from '@angular/core';
import { FileUploadService } from './file-upload.service';
import { HttpClient } from '@angular/common/http';
import {categoryUrl} from '../apiUrls'

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
  // 1) Get All categfories
  // 2) Add the Article
  // 3) add the photo
  // 4) add the categories
export class AddArticleComponent implements OnInit{
  public nom = '';
  public quantity = 0;
  public description = "";
  categories: any = [];
  categoriesSelected : any = [];
  file: any = null; 
  loading: boolean = false; // Flag variable
  shortLink: string = "";
  errorMessage = "";
  
  constructor(private fileUploadService: FileUploadService,private http: HttpClient) { }
  idArticle = 0;


  ngOnInit(){
    this.http.get<any>(categoryUrl).subscribe(data => {
            this.categories.push(data)
            console.log(this.categories)
    })
  }

  onClick(){
    this.uploadArticle();
    
  }

  onChange(event :any) {
    this.file = event.target.files[0];
  }
  uploadArticle() {
    this.fileUploadService.uploadArticle(this.nom,this.quantity,this.description)
    .subscribe(
      (data) => {                           //Next callback
          this.idArticle = data;
          console.log("%c id Article = "+this.idArticle, "color : green")
          if(this.file!=null){
            this.uploadImage(data);
          }
      },
      (error) => {                              //Error callback
        console.log(error.error)
        this.errorMessage = error;
      }
    );
    
  }

  uploadImage(idArticle:number) {
    this.loading = !this.loading;
    //console.log(this.file);
    console.log("%c id Article = "+idArticle, "color : red")
    this.fileUploadService.uploadFile(this.file,idArticle).subscribe(
        (data: any) => {
          
          // Short link via api response
          this.shortLink = data;

          this.loading = false; // Flag variable 
          console.log("%c"+this.shortLink, "color : red")
          this.uploadCat(idArticle);
        }
    );
  }
  uploadCat(idArticle:number){
    for(let i=0; i<this.categoriesSelected.length; i++){
      this.fileUploadService.uploadCat(this.categoriesSelected[i],idArticle).subscribe(
        (response:any)=>{
          console.log(response)
        }
      )
    }
  }
  getCatChange(cat:any){
    let exist = false;
    for(let i = 0; i<this.categoriesSelected.length;i++){
      if(this.categoriesSelected[i] == cat.idCat){
        exist = true;
        this.categoriesSelected.splice(i, 1);
      }
        
    }
    if(!exist){
      this.categoriesSelected.push(cat.idCat)
    }
    console.log(this.categoriesSelected)
  }
}
