import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { FileUpload } from '../model/file-upload';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-files-grid',
  templateUrl: './files-grid.component.html',
  styleUrls: ['./files-grid.component.css']
})
export class FilesGridComponent implements OnInit {

  files: Observable<FileUpload[]>;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
      this.files = this.apiService.findAllUploadedFiles();
  }

  public download(a, b): void {
    console.log('teste');
  }

}
