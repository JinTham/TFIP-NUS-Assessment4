import { Component, OnInit } from '@angular/core';
import { UploadFileService } from '../services/upload-file.service';
import { Bundle } from '../models/bundle';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit{

  bundles!:Bundle[]

  constructor(private uploadFileSvc:UploadFileService){ }

  async ngOnInit() {
    this.bundles = await this.uploadFileSvc.getAllBundles()
    console.log(this.bundles)
  }

}
