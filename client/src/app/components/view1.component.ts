import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UploadFileService } from '../services/upload-file.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { UploadFile } from '../models/uploadFile';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit{

  form!:FormGroup
  @ViewChild('file') imageFile!: ElementRef;

  constructor(private fb:FormBuilder, private uploadFileSvc:UploadFileService, private httpClient:HttpClient){ }

  ngOnInit(): void {
      this.form = this.createForm()
  }

  createForm():FormGroup {
    return this.fb.group({
      name:this.fb.control('',Validators.required),
      title:this.fb.control('',Validators.required),
      comments:this.fb.control(''),
      'image-file':this.fb.control('')
    })
  }

  processForm() {
    const formData = new FormData()
    formData.set("name",this.form.value['name'])
    // formData.set("title",this.form.value['title'])
    // formData.set("comments",this.form.value['comments'])
    // formData.set('archive', this.imageFile.nativeElement.files[0])
    // console.log(formData.get("name"))
    // console.log(formData.get("title"))
    // console.log(formData.get("comments"))
    // console.log(formData.get("archive"))
    // const uploadFile = this.form.value
    // console.log(uploadFile)
    
    // formData.set("name",uploadFile['name'])
    // formData.set("title",uploadFile['title'])
    // formData.set("comments",uploadFile['comments'])
    // formData.set('archive', this.imageFile.nativeElement.files[0])
    const headers = new HttpHeaders().set("Content-Type","multipart/form-data; boundary=----0YsU72sGdwPe5B")
    return firstValueFrom(this.httpClient.post<UploadFile>('/upload', formData, {headers:headers}))
  }

  // upload(name:string, title:string, comments:string, zip:ElementRef) {
  //   const formData = new FormData()
  //   formData.set("name",name)
  //   formData.set("title",title)
  //   formData.set("comments",comments)
  //   formData.set("archive",zip.nativeElement.files[0])
  //   console.log(formData.get('archive'))
  //   const headers = new HttpHeaders().set("Content-Type","multipart/form-data")
  //   return firstValueFrom(this.httpClient.post('/upload', formData, {headers:headers}))
  // }

}
