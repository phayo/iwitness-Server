import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './device.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeviceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceDetail = (props: IDeviceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { deviceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deviceDetailsHeading">
          <Translate contentKey="iwitnessApp.device.detail.title">Device</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="iwitnessApp.device.type">Type</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.type}</dd>
          <dt>
            <span id="blacklisted">
              <Translate contentKey="iwitnessApp.device.blacklisted">Blacklisted</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.blacklisted ? 'true' : 'false'}</dd>
          <dt>
            <span id="hash">
              <Translate contentKey="iwitnessApp.device.hash">Hash</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.hash}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="iwitnessApp.device.name">Name</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.name}</dd>
          <dt>
            <span id="linkDate">
              <Translate contentKey="iwitnessApp.device.linkDate">Link Date</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.linkDate ? <TextFormat value={deviceEntity.linkDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="blacklistDate">
              <Translate contentKey="iwitnessApp.device.blacklistDate">Blacklist Date</Translate>
            </span>
          </dt>
          <dd>
            {deviceEntity.blacklistDate ? <TextFormat value={deviceEntity.blacklistDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="blacklistReason">
              <Translate contentKey="iwitnessApp.device.blacklistReason">Blacklist Reason</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.blacklistReason}</dd>
          <dt>
            <span id="imei">
              <Translate contentKey="iwitnessApp.device.imei">Imei</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.imei}</dd>
          <dt>
            <span id="cameraPermGranted">
              <Translate contentKey="iwitnessApp.device.cameraPermGranted">Camera Perm Granted</Translate>
            </span>
          </dt>
          <dd>
            {deviceEntity.cameraPermGranted ? (
              <TextFormat value={deviceEntity.cameraPermGranted} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="locationPermGranted">
              <Translate contentKey="iwitnessApp.device.locationPermGranted">Location Perm Granted</Translate>
            </span>
          </dt>
          <dd>
            {deviceEntity.locationPermGranted ? (
              <TextFormat value={deviceEntity.locationPermGranted} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bgInternetPermGranted">
              <Translate contentKey="iwitnessApp.device.bgInternetPermGranted">Bg Internet Perm Granted</Translate>
            </span>
          </dt>
          <dd>
            {deviceEntity.bgInternetPermGranted ? (
              <TextFormat value={deviceEntity.bgInternetPermGranted} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="iwitnessApp.device.status">Status</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.status}</dd>
          <dt>
            <span id="os">
              <Translate contentKey="iwitnessApp.device.os">Os</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.os}</dd>
          <dt>
            <Translate contentKey="iwitnessApp.device.capture">Capture</Translate>
          </dt>
          <dd>{deviceEntity.capture ? deviceEntity.capture.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/device" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/device/${deviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ device }: IRootState) => ({
  deviceEntity: device.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceDetail);
