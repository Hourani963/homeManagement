import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'
import { HttpClient } from '@angular/common/http';
import {categoryUrl} from '../../apiUrls'
import { FileUploadService } from '../../add-article/file-upload.service';

@Component({
  selector: 'app-cat-menu',
  templateUrl: './cat-menu.component.html',
  styleUrls: ['./cat-menu.component.scss']
})
export class CatMenuComponent implements OnInit{

  categories: any = [];
  categoriesSelected : any = [];
  articleWithCats : any = [];
  constructor(private router : Router,private http: HttpClient,private fileUploadService: FileUploadService){}

  ngOnInit(){
    this.http.get<any>(categoryUrl).subscribe(data => {
            this.categories.push(data)
            console.log(this.categories)
    })
  }


  getArtsForCats(){
    for(let i=0; i<this.categoriesSelected.length; i++){
      this.fileUploadService.getArtForCat(this.categoriesSelected[i]).subscribe(
        (data: any) => {
          this.articleWithCats.push(data);
        }
    );
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
