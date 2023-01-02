import { Component } from '@angular/core';
import { FileUploadService } from '../add-article/file-upload.service';

@Component({
  selector: 'app-add-cat',
  templateUrl: './add-cat.component.html',
  styleUrls: ['./add-cat.component.scss']
})
export class AddCatComponent {

  public nomCat = '';
  public errorMessage = '';
  public catCreatedStat = false;
  public serverMessage = '';
  constructor(private fileUploadService: FileUploadService){}

  onClick(){
    this.fileUploadService.uploadCat(this.nomCat)
    .subscribe(
      (data) => {                           //Next callback

          console.log("%c"+data, "color : green")
          this.catCreatedStat = true;
          this.serverMessage = data;
          return this.serverMessage;
      },
      (error) => {                              //Error callback
        console.log(error.error)
        this.errorMessage = error;
        this.catCreatedStat = false;

        return this.serverMessage = error.error
      }
    );
  }
}
