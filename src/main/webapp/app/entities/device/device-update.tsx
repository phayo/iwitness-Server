import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICapture } from 'app/shared/model/capture.model';
import { getEntities as getCaptures } from 'app/entities/capture/capture.reducer';
import { getEntity, updateEntity, createEntity, reset } from './device.reducer';
import { IDevice } from 'app/shared/model/device.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDeviceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceUpdate = (props: IDeviceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { deviceEntity, captures, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/device');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCaptures();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.linkDate = convertDateTimeToServer(values.linkDate);
    values.blacklistDate = convertDateTimeToServer(values.blacklistDate);
    values.cameraPermGranted = convertDateTimeToServer(values.cameraPermGranted);
    values.locationPermGranted = convertDateTimeToServer(values.locationPermGranted);
    values.bgInternetPermGranted = convertDateTimeToServer(values.bgInternetPermGranted);

    if (errors.length === 0) {
      const entity = {
        ...deviceEntity,
        ...values,
        capture: captures.find(it => it.id.toString() === values.captureId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="iwitnessApp.device.home.createOrEditLabel" data-cy="DeviceCreateUpdateHeading">
            <Translate contentKey="iwitnessApp.device.home.createOrEditLabel">Create or edit a Device</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : deviceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="device-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="device-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="device-type">
                  <Translate contentKey="iwitnessApp.device.type">Type</Translate>
                </Label>
                <AvField id="device-type" data-cy="type" type="text" name="type" />
              </AvGroup>
              <AvGroup check>
                <Label id="blacklistedLabel">
                  <AvInput id="device-blacklisted" data-cy="blacklisted" type="checkbox" className="form-check-input" name="blacklisted" />
                  <Translate contentKey="iwitnessApp.device.blacklisted">Blacklisted</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="hashLabel" for="device-hash">
                  <Translate contentKey="iwitnessApp.device.hash">Hash</Translate>
                </Label>
                <AvField id="device-hash" data-cy="hash" type="text" name="hash" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="device-name">
                  <Translate contentKey="iwitnessApp.device.name">Name</Translate>
                </Label>
                <AvField id="device-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="linkDateLabel" for="device-linkDate">
                  <Translate contentKey="iwitnessApp.device.linkDate">Link Date</Translate>
                </Label>
                <AvInput
                  id="device-linkDate"
                  data-cy="linkDate"
                  type="datetime-local"
                  className="form-control"
                  name="linkDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.deviceEntity.linkDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="blacklistDateLabel" for="device-blacklistDate">
                  <Translate contentKey="iwitnessApp.device.blacklistDate">Blacklist Date</Translate>
                </Label>
                <AvInput
                  id="device-blacklistDate"
                  data-cy="blacklistDate"
                  type="datetime-local"
                  className="form-control"
                  name="blacklistDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.deviceEntity.blacklistDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="blacklistReasonLabel" for="device-blacklistReason">
                  <Translate contentKey="iwitnessApp.device.blacklistReason">Blacklist Reason</Translate>
                </Label>
                <AvField id="device-blacklistReason" data-cy="blacklistReason" type="text" name="blacklistReason" />
              </AvGroup>
              <AvGroup>
                <Label id="imeiLabel" for="device-imei">
                  <Translate contentKey="iwitnessApp.device.imei">Imei</Translate>
                </Label>
                <AvField id="device-imei" data-cy="imei" type="text" name="imei" />
              </AvGroup>
              <AvGroup>
                <Label id="cameraPermGrantedLabel" for="device-cameraPermGranted">
                  <Translate contentKey="iwitnessApp.device.cameraPermGranted">Camera Perm Granted</Translate>
                </Label>
                <AvInput
                  id="device-cameraPermGranted"
                  data-cy="cameraPermGranted"
                  type="datetime-local"
                  className="form-control"
                  name="cameraPermGranted"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.deviceEntity.cameraPermGranted)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="locationPermGrantedLabel" for="device-locationPermGranted">
                  <Translate contentKey="iwitnessApp.device.locationPermGranted">Location Perm Granted</Translate>
                </Label>
                <AvInput
                  id="device-locationPermGranted"
                  data-cy="locationPermGranted"
                  type="datetime-local"
                  className="form-control"
                  name="locationPermGranted"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.deviceEntity.locationPermGranted)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="bgInternetPermGrantedLabel" for="device-bgInternetPermGranted">
                  <Translate contentKey="iwitnessApp.device.bgInternetPermGranted">Bg Internet Perm Granted</Translate>
                </Label>
                <AvInput
                  id="device-bgInternetPermGranted"
                  data-cy="bgInternetPermGranted"
                  type="datetime-local"
                  className="form-control"
                  name="bgInternetPermGranted"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.deviceEntity.bgInternetPermGranted)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="device-status">
                  <Translate contentKey="iwitnessApp.device.status">Status</Translate>
                </Label>
                <AvInput
                  id="device-status"
                  data-cy="status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && deviceEntity.status) || 'BLACKLISTED'}
                >
                  <option value="BLACKLISTED">{translate('iwitnessApp.DeviceStatus.BLACKLISTED')}</option>
                  <option value="UNVERIFIED">{translate('iwitnessApp.DeviceStatus.UNVERIFIED')}</option>
                  <option value="TRUSTED">{translate('iwitnessApp.DeviceStatus.TRUSTED')}</option>
                  <option value="LINKED">{translate('iwitnessApp.DeviceStatus.LINKED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="osLabel" for="device-os">
                  <Translate contentKey="iwitnessApp.device.os">Os</Translate>
                </Label>
                <AvInput
                  id="device-os"
                  data-cy="os"
                  type="select"
                  className="form-control"
                  name="os"
                  value={(!isNew && deviceEntity.os) || 'ANDROID_OS'}
                >
                  <option value="ANDROID_OS">{translate('iwitnessApp.OS.ANDROID_OS')}</option>
                  <option value="IOS">{translate('iwitnessApp.OS.IOS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="device-capture">
                  <Translate contentKey="iwitnessApp.device.capture">Capture</Translate>
                </Label>
                <AvInput id="device-capture" data-cy="capture" type="select" className="form-control" name="captureId">
                  <option value="" key="0" />
                  {captures
                    ? captures.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/device" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  captures: storeState.capture.entities,
  deviceEntity: storeState.device.entity,
  loading: storeState.device.loading,
  updating: storeState.device.updating,
  updateSuccess: storeState.device.updateSuccess,
});

const mapDispatchToProps = {
  getCaptures,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceUpdate);
