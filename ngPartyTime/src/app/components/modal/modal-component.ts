import { Component, inject, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MapModule } from '../../map.module';

@Component({
  selector: 'ngbd-modal-content',
  standalone: true,
  imports: [MapModule],
  template: `
    <div class="modal-header">
      <button
        type="button"
        class="btn-close"
        aria-label="Close"
        (click)="activeModal.dismiss('Cross click')"
      ></button>
    </div>
    <div class="modal-body">
      <!-- <app-map></app-map> -->
      <h1>Hello, {{ name }}!</h1>
    </div>
    <div class="modal-footer">
      <button
        type="button"
        class="btn btn-outline-secondary"
        (click)="activeModal.close('Close click')"
      >
        Close
      </button>
    </div>
  `,
})
export class NgbdModalContent {
  activeModal = inject(NgbActiveModal);

  @Input() name: string = '';
}

@Component({
  selector: 'ngbd-modal-component',
  standalone: true,
  templateUrl: './modal-component.html',
})
export class NgbdModalComponent {
  private modalService = inject(NgbModal);

  open(): void {
    const modalRef = this.modalService.open(NgbdModalContent);
    modalRef.componentInstance.name =
      ' World ' + new Date().toLocaleDateString();
  }
}
