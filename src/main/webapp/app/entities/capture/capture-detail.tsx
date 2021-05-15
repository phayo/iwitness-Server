import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './capture.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { DurationFormat } from 'app/shared/DurationFormat';

export interface ICaptureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CaptureDetail = (props: ICaptureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { captureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="captureDetailsHeading">
          <Translate contentKey="iwitnessApp.capture.detail.title">Capture</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{captureEntity.id}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="iwitnessApp.capture.duration">Duration</Translate>
            </span>
          </dt>
          <dd>
            {captureEntity.duration ? <DurationFormat value={captureEntity.duration} /> : null} ({captureEntity.duration})
          </dd>
          <dt>
            <span id="title">
              <Translate contentKey="iwitnessApp.capture.title">Title</Translate>
            </span>
          </dt>
          <dd>{captureEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="iwitnessApp.capture.description">Description</Translate>
            </span>
          </dt>
          <dd>{captureEntity.description}</dd>
          <dt>
            <span id="cloudUrl">
              <Translate contentKey="iwitnessApp.capture.cloudUrl">Cloud Url</Translate>
            </span>
          </dt>
          <dd>{captureEntity.cloudUrl}</dd>
          <dt>
            <span id="recordStartTime">
              <Translate contentKey="iwitnessApp.capture.recordStartTime">Record Start Time</Translate>
            </span>
          </dt>
          <dd>
            {captureEntity.recordStartTime ? (
              <TextFormat value={captureEntity.recordStartTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="recordEndTime">
              <Translate contentKey="iwitnessApp.capture.recordEndTime">Record End Time</Translate>
            </span>
          </dt>
          <dd>
            {captureEntity.recordEndTime ? <TextFormat value={captureEntity.recordEndTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="cloudUploadStartTime">
              <Translate contentKey="iwitnessApp.capture.cloudUploadStartTime">Cloud Upload Start Time</Translate>
            </span>
          </dt>
          <dd>
            {captureEntity.cloudUploadStartTime ? (
              <TextFormat value={captureEntity.cloudUploadStartTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cloudUploadploadEndTime">
              <Translate contentKey="iwitnessApp.capture.cloudUploadploadEndTime">Cloud Uploadpload End Time</Translate>
            </span>
          </dt>
          <dd>
            {captureEntity.cloudUploadploadEndTime ? (
              <TextFormat value={captureEntity.cloudUploadploadEndTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="serverUploadTime">
              <Translate contentKey="iwitnessApp.capture.serverUploadTime">Server Upload Time</Translate>
            </span>
          </dt>
          <dd>
            {captureEntity.serverUploadTime ? (
              <TextFormat value={captureEntity.serverUploadTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="publicHash">
              <Translate contentKey="iwitnessApp.capture.publicHash">Public Hash</Translate>
            </span>
          </dt>
          <dd>{captureEntity.publicHash}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="iwitnessApp.capture.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{captureEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="iwitnessApp.capture.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{captureEntity.longitude}</dd>
          <dt>
            <span id="hash">
              <Translate contentKey="iwitnessApp.capture.hash">Hash</Translate>
            </span>
          </dt>
          <dd>{captureEntity.hash}</dd>
          <dt>
            <span id="captureMode">
              <Translate contentKey="iwitnessApp.capture.captureMode">Capture Mode</Translate>
            </span>
          </dt>
          <dd>{captureEntity.captureMode}</dd>
        </dl>
        <Button tag={Link} to="/capture" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/capture/${captureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ capture }: IRootState) => ({
  captureEntity: capture.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CaptureDetail);
