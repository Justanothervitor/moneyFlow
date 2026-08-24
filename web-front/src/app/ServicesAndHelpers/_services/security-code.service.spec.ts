import { TestBed } from '@angular/core/testing';

import { SecurityCodeService } from './security-code.service';

describe('SecurityCodeService', () => {
  let service: SecurityCodeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecurityCodeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
