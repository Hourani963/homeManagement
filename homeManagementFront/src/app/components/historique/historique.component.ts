import {AfterViewInit, Component, ViewChild, OnInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { HistoriqueService } from './historique.service';
import { first, tap } from 'rxjs';


@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.scss']
})
export class HistoriqueComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['id article', 'nom Article', 'ancienne quantité', 'nouvelle quantité', "date"];


  public dataSource: any;
  public historyOnePage : any = [];
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private histoService : HistoriqueService) {

    
  }
  ngOnInit(): void {
    this.histoService.getHistorique().subscribe(
      (data) => {
        this.dataSource = data
        this.leadOnePage()
      }
    )
    
  }


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.paginator.page.pipe(
      tap(()=>this.leadOnePage())
    ).subscribe();
  }
  leadOnePage(){

    var firstElemnt = this.paginator? this.paginator.pageIndex*this.paginator.pageSize:0;
    var lastElemnt = this.paginator? ((this.paginator.pageIndex*this.paginator.pageSize) + this.paginator.pageSize) : 10;

      var articlesOnePage : any = []
      for(let i=firstElemnt; i<lastElemnt; i++){
          if(i<this.dataSource.length){
            articlesOnePage.push(this.dataSource[i])
          }
      }
      this.historyOnePage = []

      this.historyOnePage.push(articlesOnePage)
      
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
