import { Component, OnInit } from '@angular/core';
import { FileService } from '../service/file.service';
import { FileUpload } from '../model/file-upload';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-files-grid',
  templateUrl: './files-grid.component.html',
  styleUrls: ['./files-grid.component.css']
})
export class FilesGridComponent implements OnInit {

  files: Observable<FileUpload[]>;

  constructor(private fileService: FileService) { }

  ngOnInit() {
      this.files = this.fileService.findAllUploadedFiles();
  }

}
