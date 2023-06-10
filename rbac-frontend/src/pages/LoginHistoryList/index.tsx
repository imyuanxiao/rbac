import {
  getDataList,
} from '@/services/ant-design-pro/api';
import type {ActionType, ProColumns, ProDescriptionsItemProps} from '@ant-design/pro-components';
import {
  FooterToolbar,
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Drawer} from 'antd';
import React, { useRef, useState } from 'react';
import {orgEnum} from "@/utils/comonValue";

const DataList: React.FC = () => {

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DataListItem>();
  const [selectedRowsState, setSelectedRows] = useState<API.DataListItem[]>([]);

  const [currentPage, setCurrentPage] = useState(1); // 当前页码
  const [pageSize, setPageSize] = useState(10); // 每页显示的数据数量

  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */
  const intl = useIntl();

  const columns: ProColumns<API.DataListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      hideInTable: true, // 隐藏该列
    },
    {
      title: (
        <FormattedMessage
          id="pages.searchTable.updateForm.index"
          defaultMessage="序号"
        />
      ),
      dataIndex: 'index',
      render: (dom, entity, index) => {
        const realIndex = (currentPage - 1) * pageSize + index + 1; // 计算真实的序号
        return realIndex;
      },
    },
    {
      title: (
        <FormattedMessage
          id="pages.searchTable.updateForm.dataName"
          defaultMessage="数据"
        />
      ),
      dataIndex: 'dataName',
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
      title: <FormattedMessage id="pages.searchTable.updateForm.orgId" defaultMessage="组织" />,
      dataIndex: 'orgId',
      valueType: 'select',
      valueEnum: orgEnum,
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DataListItem, API.PageParams>
        headerTitle={intl.formatMessage({
          id: 'pages.searchTable.title',
          defaultMessage: '查询信息',
        })}
        actionRef={actionRef}
        rowKey="id"
        search={false}
        request={(params) => {
          // 更新当前页码和每页显示数量
          setCurrentPage(params.current || 1);
          setPageSize(params.pageSize || 10);
          // 调用实际的请求方法，传递 params 参数
          return getDataList(params);
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
        </FooterToolbar>
      )}

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.id && (
          <ProDescriptions<API.DataListItem>
            column={2}
            title={
              <FormattedMessage id="pages.dataDetail.title" defaultMessage="数据详情" />
            }
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<API.DataListItem>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default DataList;
