import { Component, OnInit, Inject,AfterViewInit,ViewChild } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {ActivatedRoute, ParamMap, Router} from'@angular/router'
import {articleUrl} from '../apiUrls'
import {DataService} from './data.service'
import { first, tap } from 'rxjs';
import {MatPaginator} from'@angular/material/paginator'
import {MatTableDataSource}from '@angular/material/table'


export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

@Component({
  selector: 'app-article-profile',
  templateUrl: './article-profile.component.html',
  styleUrls: ['./article-profile.component.scss']
})
export class ArticleProfileComponent implements OnInit, AfterViewInit{


  public idArt : number = 0;
  public articleURL = articleUrl;

  public photoList : any = []
  public videoList : any = []

  public photoCount = 0;
  public videoCount = 0;

  public showPhotos : boolean = true;
  public showVideos : boolean = false;

  public articleInfo : any = []
 
  public aAGE : number = 0;


  public onePageElements : any = [];

  dataSourcePhoto = new MatTableDataSource(this.photoList)
  dataSourceVideo = new MatTableDataSource(this.videoList)
  @ViewChild('paginatorPhoto') paginatorPhoto : MatPaginator
  @ViewChild('paginatorVideo') paginatorVideo : MatPaginator

  constructor(private route:ActivatedRoute,private routeNavigate : Router, private dataService : DataService,public dialog: MatDialog ){}

  ngOnInit(): void {
    this.dataSourcePhoto.paginator = this.paginatorPhoto
    this.dataSourceVideo.paginator = this.paginatorVideo
    let id =  parseInt(this.route.snapshot.params['idArt']);
    this.idArt = id;

    this.dataService.getPerformerById(id).subscribe(
      (data:any) =>{
        this.articleInfo.push(data)
      }
    )
    this.dataService.getVideoList(this.idArt).subscribe(
      (data :any) =>{
        if(data.length > 0){
          this.videoCount = data.length
          this.videoList.push(data)
          this.leadOnePage();
        }
        
      }
    )
    this.dataService.getPhotoList(this.idArt).subscribe(
      (data:any) =>{
        if(data.length > 0){
          this.photoCount = data.length
          this.photoList.push(data)
          this.leadOnePage();
        }
        
      }
    )

    

  }
  ngAfterViewInit(): void {
    if(this.showPhotos){
      this.paginatorPhoto.page.pipe(
        tap(()=>this.leadOnePage())
      ).subscribe();
    }
    if(this.showVideos){
      this.paginatorVideo.page.pipe(
        tap(()=>this.leadOnePage())
      ).subscribe();
    }
    
    
  }

   leadOnePage(){
    var firstElemnt = 0
    var lastElemnt = 0

      firstElemnt = this.paginatorPhoto? this.paginatorPhoto.pageIndex*this.paginatorPhoto.pageSize:0;
      lastElemnt = this.paginatorPhoto? ((this.paginatorPhoto.pageIndex*this.paginatorPhoto.pageSize) + this.paginatorPhoto.pageSize) : 20;

    var listPhotosVideosNull = true;
    // console.log("%c first Element = "+firstElemnt ,"color:green")
    // console.log("%c first Element = "+lastElemnt ,"color:red")
      var articlesOnePage : any = []
      
    
      //console.log("%c length = "+this.videoList[0] ,"color:green")
      if(this.showPhotos && this.photoCount > 0){
        listPhotosVideosNull = false;
        for(let i=firstElemnt; i<lastElemnt; i++){
          if(i<this.photoList[0].length){
            articlesOnePage.push(this.photoList[0][i])
          }
        }

        
      }
      
      else if(this.showVideos && this.videoCount > 0){
        listPhotosVideosNull = false;
        for(let i=firstElemnt; i<lastElemnt; i++){
          if(i<this.videoList[0].length){
            articlesOnePage.push(this.videoList[0][i])
          }
      }
    }
      if(!listPhotosVideosNull){
        this.onePageElements = [];
        this.onePageElements.push(articlesOnePage)
        //console.log(this.onePageElements)
      }else{
        this.onePageElements[0]= []
        //console.log("herre")
       // console.log(this.onePageElements)
      }
      

      
  }

  showallPhotos(){
    this.showPhotos = true;
    this.showVideos = false;
    this.leadOnePage()

  }
  showallVideos(){
    this.showPhotos = false;
    this.showVideos = true;
    this.leadOnePage()

    
  }

  addPhoto(){
    this.dialog.open(DialogAddPhoto, {
      width: '300px',
      data: {idArt: this.idArt},
    });
  }
  addVideo(){
    this.dialog.open(DialogAddVideo, {
      width: '300px',
      data: {idArt: this.idArt},
    });
  }


  tiles: Tile[] = [
    {text: 'infos', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'buttons', cols: 3, rows: 1, color: '#DDBDF1'},
    
  ];

  onClickImage(image: any){
    console.log(image)
    window.open(image.src)
  }

  deleteVideo(nomPerformer : string, nomVid : string){

    this.dialog.open(DialogDeleteElement, {
      width: '300px',
      data: {nomPerformer: nomPerformer, nomVid : nomVid, isImage : false},
    });
    
  }
  deleteImage(nomPerformer : string, nomVid : string){
    this.dialog.open(DialogDeleteElement, {
      width: '400px',
      height : '200px',
      data: {nomPerformer: nomPerformer, nomVid : nomVid, isImage : true},
    });

  }
}



@Component({
  selector: 'dialog-animations-example-dialog',
  templateUrl: 'dialog-add-photo.html',
})
export class DialogAddPhoto {

  file: any = null; 
  public responseMessage = "";
  constructor(public dialogRef: MatDialogRef<DialogAddPhoto>,
    private dataService : DataService,
    @Inject(MAT_DIALOG_DATA) public data: any) {}


  onChange(event :any) {
      this.file = event.target.files[0];
  }

  onClickPhoto(){
    //console.log(this.data.idArt)
    this.dataService.addPhotoToPerformer(this.data.idArt,this.file).subscribe(
      data => this.responseMessage= data
    )
  }

}

@Component({
  selector: 'dialog-animations-example-dialog',
  templateUrl: 'dialog-add-photo.html',
})
export class DialogAddVideo {

  file: any = null; 
  public responseMessage = "";
  constructor(public dialogRef: MatDialogRef<DialogAddPhoto>,
    private dataService : DataService,
    @Inject(MAT_DIALOG_DATA) public data: any) {}


  onChange(event :any) {
      this.file = event.target.files[0];
  }

  onClickPhoto(){
    //console.log(this.data.idArt)
    this.dataService.addVideoToPerformer(this.data.idArt,this.file).subscribe(
      data => this.responseMessage= data
    )
  }

}

@Component({
  selector: 'dialog-animations-example-dialog',
  templateUrl: 'dialog-delete-element.html',
})
export class DialogDeleteElement {
  constructor(public dialogRef: MatDialogRef<DialogDeleteElement>,
    private dataService : DataService,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

    public responseMessage : any = "not deleted";

    confirmDelete(){
      //console.log(this.data)
      if(this.data.isImage){
        this.dataService.deleteImage(this.data.nomPerformer,this.data.nomVid).subscribe(
          (data:any)=> {
            console.log(data)
            this.responseMessage = data
          }
          )
      }else{
        this.dataService.deleteVideo(this.data.nomPerformer,this.data.nomVid).subscribe(
          (data:any)=> {
            console.log(data)
            this.responseMessage = data
          }
          )
      }
      
    }
}