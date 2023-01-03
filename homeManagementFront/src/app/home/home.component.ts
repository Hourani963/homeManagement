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



  constructor(private router : Router){}

  ngOnInit(){
    
  }


  // navigation general controled by the html
  onClick(endPoint:string){
    this.router.navigate([endPoint])
  }

  
  tiles: Tile[] = [
    {text: 'buttons', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'category', cols: 3, rows: 1, color: '#DDBDF1'},
    {text: 'articles', cols: 4, rows: 8, color: '#DDBDF1'},
  ];



}

