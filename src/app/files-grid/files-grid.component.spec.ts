import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilesGridComponent } from './files-grid.component';

describe('FilesGridComponent', () => {
  let component: FilesGridComponent;
  let fixture: ComponentFixture<FilesGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilesGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilesGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
