import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  public onUploadError(e) {
    console.log('Error');
  }

  public onUploadSuccess(e) {
    console.log('Succerss');
  }

}
