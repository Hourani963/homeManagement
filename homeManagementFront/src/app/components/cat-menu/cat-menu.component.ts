import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'
import { HttpClient } from '@angular/common/http';
import {categoryUrl} from '../../apiUrls'


@Component({
  selector: 'app-cat-menu',
  templateUrl: './cat-menu.component.html',
  styleUrls: ['./cat-menu.component.scss']
})
export class CatMenuComponent implements OnInit{

  categories: any = [];

  constructor(private router : Router,private http: HttpClient){}

  ngOnInit(){
    this.http.get<any>(categoryUrl).subscribe(data => {
            this.categories.push(data)
            console.log(this.categories)
    })
  }
  
}
