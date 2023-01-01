import { Component } from '@angular/core';
import { Uppy } from '@uppy/core'
import DragDrop from '@uppy/drag-drop'


@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent {
  public nom = '';
  public quantity = 0;
  public description = "";
  file: any = null; 


  onClick(){
    console.log(this.file)
  }
  onChange(event :any) {
    this.file = event.target.files[0];
}

}
