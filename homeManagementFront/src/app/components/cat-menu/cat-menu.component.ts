import { Component, OnInit, Output,EventEmitter } from '@angular/core';
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
  public categoriesSelected : any = [];
  public articleWithCats : any = [];
  constructor(private router : Router,private http: HttpClient,private fileUploadService: FileUploadService){}

  @Output() public childEvent = new EventEmitter()

  ngOnInit(){
    this.http.get<any>(categoryUrl).subscribe(data => {
            this.categories.push(data)
            
    })
  }

  // send articleWithCats to the parentComponent (homeComponent)
  fireEvent(articleForCats : any = []){
      
      if(articleForCats.length == this.categoriesSelected.length){
        //console.log(articleForCats)
        this.childEvent.emit(articleForCats);
      }
        

  }

  getArtsForCats(){
    this.articleWithCats = []
    for(let i=0; i<this.categoriesSelected.length; i++){
      this.fileUploadService.getArtForCat(this.categoriesSelected[i]).subscribe(
        (data) => {
          this.articleWithCats.push(data);
          //console.log("length= "+this.articleWithCats.length)
          this.fireEvent(this.articleWithCats);
        }
        
    );
    }
    const nullArticle : any = [];
    this.fireEvent(nullArticle)
    
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
    //console.log(this.categoriesSelected)
    this.getArtsForCats();
  }
  
  
}
