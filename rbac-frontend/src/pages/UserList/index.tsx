import {
  addUser, getRoleList,
  getUserListByConditions,
  removeUser,
  updateUser
} from '@/services/ant-design-pro/api';
import { PlusOutlined } from '@ant-design/icons';
import type {ActionType, ProColumns, ProDescriptionsItemProps, ProFormInstance} from '@ant-design/pro-components';
import {
  FooterToolbar,
  ModalForm,
  PageContainer,
  ProDescriptions, ProFormSelect,
  ProFormText,
  ProTable,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Button, Drawer, message} from 'antd';
import React, { useRef, useState } from 'react';
import UpdateForm from './components/UpdateForm';
import {orgEnum, userStatusEnum, userStatusOptions} from "@/utils/comonValue";
import { useAccess, Access } from 'umi';

/**
 * @en-US Add node
 * @zh-CN 添加节点
 * @param fields
 */
const handleAdd = async (fields: API.UserListItem) => {
  const hide = message.loading('正在添加');
  try {
    await addUser({
      ...fields,
      roleIds: fields.roleIds?.map(id => id.toString()),
      orgIds: fields.orgIds?.map(id => id.toString())
    });
    hide();
    message.success('Added successfully');
    return true;
  } catch (error) {
    hide();
    message.error('Adding failed, please try again!');
    return false;
  }
};

/**
 * @en-US Update node
 * @zh-CN 更新节点
 *
 * @param fields
 */
const handleUpdate = async (fields: API.UserListItem) => {
  const hide = message.loading('Updating');
  try {
    await updateUser(fields);
    hide();
    message.success('Update is successful');
    return true;
  } catch (error) {
    hide();
    message.error('Update failed, please try again!');
    return false;
  }
};

/**
 *  Delete node
 * @zh-CN 删除节点
 *
 * @param selectedRows
 */
const handleRemove = async (selectedRows: API.UserListItem[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await removeUser(selectedRows.map((row) => row.id));
    hide();
    message.success('Deleted successfully and will refresh soon');
    return true;
  } catch (error) {
    hide();
    message.error('Delete failed, please try again');
    return false;
  }
};

export async function fetchRoleOptions() {
  // 异步获取角色列表
  const res = await getRoleList();
  const roleOptions = res.response.map((role) => ({
    label: (
      <FormattedMessage
        id={`type.role.${role.roleName}`}
        defaultMessage={role.roleName}
      />
    ),
    value: role.id.toString(),
  }));
  return roleOptions;
}


const UserList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.UserListItem>();
  const [selectedRowsState, setSelectedRows] = useState<API.UserListItem[]>([]);
  const restFormRef = useRef<ProFormInstance>();

  const access = useAccess();

  const [currentPage, setCurrentPage] = useState(1); // 当前页码
  const [pageSize, setPageSize] = useState(10); // 每页显示的数据数量


  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */
  const intl = useIntl();

  const columns: ProColumns<API.UserListItem>[] = [
    {
      title: (
        <FormattedMessage
          id="typings.UserListItem.id"
          defaultMessage="User ID"
        />
      ),
      dataIndex: 'id',
      hideInTable: true, // 隐藏该列
      hideInSearch: true, // 隐藏搜索条件
    },
    {
      title: (
        <FormattedMessage
          id="common.index"
          defaultMessage="序号"
        />
      ),
      dataIndex: 'index',
      hideInSearch: true,
      render: (dom, entity, index) => {
        const realIndex = (currentPage - 1) * pageSize + index + 1; // 计算真实的序号
        return realIndex;
      },
    },
    {
      title: (
        <FormattedMessage
          id="typings.UserListItem.username"
          defaultMessage="用户名"
        />
      ),
      dataIndex: 'username',
      tip: 'The username is the unique key',
      render: (dom, entity) => {
        return (
          <a
            onClick={() => {
              setCurrentRow(entity);
              setShowDetail(true);
            }}
          >
            {dom}
          </a>
        );
      },
    },
    {
      title: <FormattedMessage id="typings.UserListItem.userStatus" defaultMessage="Status"/>,
      dataIndex: 'userStatus',
      hideInForm: true,
      valueEnum: userStatusEnum
    },
    {
      title: <FormattedMessage id="typings.UserListItem.roleIds" defaultMessage="角色" />,
      dataIndex: 'roleIds',
      valueType: 'select',
      request: fetchRoleOptions,
      fieldProps: {
        mode: 'multiple',
      },
    },
    {
      title: <FormattedMessage id="typings.UserListItem.userPhone" defaultMessage="Phone"/>,
      dataIndex: 'userPhone',
      hideInTable: true,
      hideInForm: true,
    },
    {
      title: <FormattedMessage id="typings.UserListItem.userEmail" defaultMessage="Email"/>,
      dataIndex: 'userEmail',
      hideInForm: true,
      hideInTable: true,
    },
    {
      title: <FormattedMessage id="common.operation" defaultMessage="Operating" />,
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access
          key="editUserOption"
          accessible={access.canEditUser}>
            <a
              onClick={() => {
                setCurrentRow(record);
                handleUpdateModalOpen(true);
              }}
            >
                <FormattedMessage id="common.operation.edit" defaultMessage="编辑" />
            </a>
        </Access>

      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.UserListItem, API.PageParams>
        headerTitle={intl.formatMessage({
          id: 'pages.searchTable.title',
          defaultMessage: '查询信息',
        })}
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Access
            key="tableAddUser"
            accessible={access.canAddUser}>
            <Button
              type="primary"
              onClick={() => {
                handleModalOpen(true);
              }}
            >
              <PlusOutlined /> <FormattedMessage id="common.operation.add" defaultMessage="新增" />
            </Button>
          </Access>,
        ]}
        request={(params) => {
          // 更新当前页码和每页显示数量
          setCurrentPage(params.current || 1);
          setPageSize(params.pageSize || 10);
          // 调用实际的请求方法，传递 params 参数
          return getUserListByConditions(params);
        }}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
        pagination={{
          pageSizeOptions: ['10', '20', '50'], // 自定义的每页显示数据数量选项
          defaultPageSize: 10, // 默认的每页显示数据数量
          showSizeChanger: true,
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              <FormattedMessage id="pages.searchTable.chosen" defaultMessage="Chosen" />{' '}
              <a style={{ fontWeight: 600 }}>{selectedRowsState.length}</a>{' '}
              <FormattedMessage id="pages.searchTable.item" defaultMessage="项" />
            </div>
          }
        >
          <Access accessible={access.canDeleteUser}>
            <Button
              onClick={async () => {
                await handleRemove(selectedRowsState);
                setSelectedRows([]);
                actionRef.current?.reloadAndRest?.();
              }}
              danger={true}
            >
                <FormattedMessage
                  id="pages.searchTable.batchDeletion"
                  defaultMessage="Batch deletion"
                />
            </Button>
        </Access>
        </FooterToolbar>
      )}
      <ModalForm
        layout="horizontal"
        title={intl.formatMessage({
          id: 'common.operation.add',
          defaultMessage: '新增',
        })}
        formRef={restFormRef}
        open={createModalOpen}
        onOpenChange={handleModalOpen}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.UserListItem);
          if (success) {
            handleModalOpen(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        submitter={{
          searchConfig: {
            resetText: '重置',
          },
          resetButtonProps: {
            onClick: () => {
              restFormRef.current?.resetFields();
            },
          },
        }}
        style={{
          display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px'
        }}
      >
          <ProFormText
            name="id"
            label={intl.formatMessage({
              id: 'typings.UserListItem.id',
              defaultMessage: 'User ID',
            })}
            hidden
            disabled
          />
          <ProFormText
            name="username"
            label={intl.formatMessage({
              id: 'typings.UserListItem.username',
              defaultMessage: 'Username',
            })}
            width="sm"
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id="pages.searchTable.updateForm.userName.nameRules"
                    defaultMessage="请输入用户名！"
                  />
                ),
              },
            ]}
          />
          <ProFormSelect
            name="userStatus"
            width="sm"
            label={intl.formatMessage({
              id: 'typings.UserListItem.userStatus',
              defaultMessage: 'Status',
            })}
            initialValue={0}
            options={userStatusOptions}
          />
          <ProFormSelect
            name="roleIds"
            mode="multiple"
            width="sm"
            label={intl.formatMessage({
              id: 'typings.UserListItem.roleIds',
              defaultMessage: 'Role',
            })}
            request={fetchRoleOptions}
          />
          <ProFormText
            name="userPhone"
            label={intl.formatMessage({
              id: 'typings.UserListItem.userPhone',
              defaultMessage: 'Phone',
            })}
            width="sm"
          />
          <ProFormText
            name="userEmail"
            label={intl.formatMessage({
              id: 'typings.UserListItem.userEmail',
              defaultMessage: 'Email',
            })}
            width="sm"
          />
          <ProFormSelect
            name="orgIds"
            mode="multiple"
            width="sm"
            label={intl.formatMessage({
              id: 'typings.UserListItem.orgIds',
              defaultMessage: 'Organization',
            })}
            valueEnum={orgEnum}
          />
      </ModalForm>
      <UpdateForm
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalOpen(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        updateModalOpen={updateModalOpen}
        values={currentRow || {}}
      />

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.username && (
          <ProDescriptions<API.UserListItem>
            column={2}
            title={
              <FormattedMessage id="pages.dataDetail.title" defaultMessage="Details" />
            }
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<API.UserListItem>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default UserList;
