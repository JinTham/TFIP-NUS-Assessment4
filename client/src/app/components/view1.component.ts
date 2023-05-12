import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UploadFileService } from '../services/upload-file.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { Bundle } from '../models/bundle';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit{

  form!:FormGroup
  bundleId!:any
  @ViewChild('file') 
  imageFile!: ElementRef;

  constructor(private fb:FormBuilder, private uploadFileSvc:UploadFileService, private httpClient:HttpClient, private router:Router){ }

  ngOnInit(): void {
      this.form = this.createForm()
  }

  createForm():FormGroup {
    return this.fb.group({
      name:this.fb.control('',Validators.required),
      title:this.fb.control('',Validators.required),
      comments:this.fb.control('')
    })
  }

  processForm() {
    const formVal = this.form.value;
    this.uploadFileSvc.upload(formVal, this.imageFile).then((result) => {
      this.bundleId = result;
      console.log(this.bundleId)
      this.router.navigate(['/view2', this.bundleId.bundleId]);
    })
  }

}
