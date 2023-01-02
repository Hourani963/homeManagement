import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'
import { FileUploadService } from '../add-article/file-upload.service';
import { HttpClient } from '@angular/common/http';
import {articleUrl} from '../apiUrls'

export interface Tile {
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

  public ARICLE_URL = articleUrl
  
  allArticles: any = [];

  constructor(private router : Router,private fileUploadService: FileUploadService,private http: HttpClient){}

  ngOnInit(){
    this.http.get<any>(articleUrl).subscribe(data => {
            this.allArticles.push(data)
            console.log(this.allArticles)
    })
  }


  // navigation general controled by the html
  onClick(endPoint:string){
    this.router.navigate([endPoint])
  }

  
  tiles: Tile[] = [
    {text: 'header', cols: 4, rows: 1},
    {text: 'buttons', cols: 1, rows: 1},
    {text: 'category', cols: 3, rows: 1},
  ];



}

