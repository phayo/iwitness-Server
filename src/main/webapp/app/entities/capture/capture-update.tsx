import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './capture.reducer';
import { ICapture } from 'app/shared/model/capture.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICaptureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CaptureUpdate = (props: ICaptureUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { captureEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/capture');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.recordStartTime = convertDateTimeToServer(values.recordStartTime);
    values.recordEndTime = convertDateTimeToServer(values.recordEndTime);
    values.cloudUploadStartTime = convertDateTimeToServer(values.cloudUploadStartTime);
    values.cloudUploadploadEndTime = convertDateTimeToServer(values.cloudUploadploadEndTime);
    values.serverUploadTime = convertDateTimeToServer(values.serverUploadTime);

    if (errors.length === 0) {
      const entity = {
        ...captureEntity,
        ...values,
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
          <h2 id="iwitnessApp.capture.home.createOrEditLabel" data-cy="CaptureCreateUpdateHeading">
            <Translate contentKey="iwitnessApp.capture.home.createOrEditLabel">Create or edit a Capture</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : captureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="capture-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="capture-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="durationLabel" for="capture-duration">
                  <Translate contentKey="iwitnessApp.capture.duration">Duration</Translate>
                </Label>
                <AvField id="capture-duration" data-cy="duration" type="text" name="duration" />
              </AvGroup>
              <AvGroup>
                <Label id="titleLabel" for="capture-title">
                  <Translate contentKey="iwitnessApp.capture.title">Title</Translate>
                </Label>
                <AvField id="capture-title" data-cy="title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="capture-description">
                  <Translate contentKey="iwitnessApp.capture.description">Description</Translate>
                </Label>
                <AvField id="capture-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="cloudUrlLabel" for="capture-cloudUrl">
                  <Translate contentKey="iwitnessApp.capture.cloudUrl">Cloud Url</Translate>
                </Label>
                <AvField id="capture-cloudUrl" data-cy="cloudUrl" type="text" name="cloudUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="recordStartTimeLabel" for="capture-recordStartTime">
                  <Translate contentKey="iwitnessApp.capture.recordStartTime">Record Start Time</Translate>
                </Label>
                <AvInput
                  id="capture-recordStartTime"
                  data-cy="recordStartTime"
                  type="datetime-local"
                  className="form-control"
                  name="recordStartTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.captureEntity.recordStartTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="recordEndTimeLabel" for="capture-recordEndTime">
                  <Translate contentKey="iwitnessApp.capture.recordEndTime">Record End Time</Translate>
                </Label>
                <AvInput
                  id="capture-recordEndTime"
                  data-cy="recordEndTime"
                  type="datetime-local"
                  className="form-control"
                  name="recordEndTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.captureEntity.recordEndTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cloudUploadStartTimeLabel" for="capture-cloudUploadStartTime">
                  <Translate contentKey="iwitnessApp.capture.cloudUploadStartTime">Cloud Upload Start Time</Translate>
                </Label>
                <AvInput
                  id="capture-cloudUploadStartTime"
                  data-cy="cloudUploadStartTime"
                  type="datetime-local"
                  className="form-control"
                  name="cloudUploadStartTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.captureEntity.cloudUploadStartTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cloudUploadploadEndTimeLabel" for="capture-cloudUploadploadEndTime">
                  <Translate contentKey="iwitnessApp.capture.cloudUploadploadEndTime">Cloud Uploadpload End Time</Translate>
                </Label>
                <AvInput
                  id="capture-cloudUploadploadEndTime"
                  data-cy="cloudUploadploadEndTime"
                  type="datetime-local"
                  className="form-control"
                  name="cloudUploadploadEndTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.captureEntity.cloudUploadploadEndTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="serverUploadTimeLabel" for="capture-serverUploadTime">
                  <Translate contentKey="iwitnessApp.capture.serverUploadTime">Server Upload Time</Translate>
                </Label>
                <AvInput
                  id="capture-serverUploadTime"
                  data-cy="serverUploadTime"
                  type="datetime-local"
                  className="form-control"
                  name="serverUploadTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.captureEntity.serverUploadTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="publicHashLabel" for="capture-publicHash">
                  <Translate contentKey="iwitnessApp.capture.publicHash">Public Hash</Translate>
                </Label>
                <AvField id="capture-publicHash" data-cy="publicHash" type="text" name="publicHash" />
              </AvGroup>
              <AvGroup>
                <Label id="latitudeLabel" for="capture-latitude">
                  <Translate contentKey="iwitnessApp.capture.latitude">Latitude</Translate>
                </Label>
                <AvField id="capture-latitude" data-cy="latitude" type="string" className="form-control" name="latitude" />
              </AvGroup>
              <AvGroup>
                <Label id="longitudeLabel" for="capture-longitude">
                  <Translate contentKey="iwitnessApp.capture.longitude">Longitude</Translate>
                </Label>
                <AvField id="capture-longitude" data-cy="longitude" type="string" className="form-control" name="longitude" />
              </AvGroup>
              <AvGroup>
                <Label id="hashLabel" for="capture-hash">
                  <Translate contentKey="iwitnessApp.capture.hash">Hash</Translate>
                </Label>
                <AvField id="capture-hash" data-cy="hash" type="text" name="hash" />
              </AvGroup>
              <AvGroup>
                <Label id="captureModeLabel" for="capture-captureMode">
                  <Translate contentKey="iwitnessApp.capture.captureMode">Capture Mode</Translate>
                </Label>
                <AvInput
                  id="capture-captureMode"
                  data-cy="captureMode"
                  type="select"
                  className="form-control"
                  name="captureMode"
                  value={(!isNew && captureEntity.captureMode) || 'DIRECT'}
                >
                  <option value="DIRECT">{translate('iwitnessApp.CaptureMode.DIRECT')}</option>
                  <option value="INDIRECT">{translate('iwitnessApp.CaptureMode.INDIRECT')}</option>
                  <option value="PANIC">{translate('iwitnessApp.CaptureMode.PANIC')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/capture" replace color="info">
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
  captureEntity: storeState.capture.entity,
  loading: storeState.capture.loading,
  updating: storeState.capture.updating,
  updateSuccess: storeState.capture.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CaptureUpdate);
