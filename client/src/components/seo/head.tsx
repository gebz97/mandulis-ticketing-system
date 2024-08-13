import { FC } from "react";
import { Helmet, HelmetData } from "react-helmet-async";

type HeadProps = {
  title?: string;
  description?: string;
};

const helmetData = new HelmetData({});

export const Head: FC<{ title: string; description: string }> = ({
  title = "",
  description = "",
}: HeadProps = {}) => {
  return (
    <Helmet
      helmetData={helmetData}
      title={title ? `${title} | Mandulis` : undefined}
      defaultTitle="Mandulis Ticketing System"
    >
      <meta name="description" content={description} />
    </Helmet>
  );
};
