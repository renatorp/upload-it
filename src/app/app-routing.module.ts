import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes} from '@angular/router';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { FilesGridComponent } from './files-grid/files-grid.component';

const routes: Routes = [
 {  path: 'upload', component: FileUploadComponent},
 {  path: 'files', component: FilesGridComponent}
];
// { path: 'dashboard', component: DashboardComponent },
// { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
// { path: 'detail/:id', component: HeroDetailComponent }

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule { }
