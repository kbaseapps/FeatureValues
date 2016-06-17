package KBaseFeatureValues::KBaseFeatureValuesClient;

use JSON::RPC::Client;
use POSIX;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
my $get_time = sub { time, 0 };
eval {
    require Time::HiRes;
    $get_time = sub { Time::HiRes::gettimeofday() };
};

use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

KBaseFeatureValues::KBaseFeatureValuesClient

=head1 DESCRIPTION


The KBaseFeatureValues set of data types and service provides a mechanism for
representing numeric values associated with genome features and conditions, together
with some basic operations on this data.  Essentially, the data is stored as a simple
2D matrix of floating point numbers.  Currently, this is exposed as support for
expression data and single gene knockout fitness data.  (Fitness data being growth
rate relative to WT growth with the specified single gene knockout in a specified
condition).

The operations supported on this data is simple clustering of genes and clustering 
related tools.


=cut

sub new
{
    my($class, $url, @args) = @_;
    

    my $self = {
	client => KBaseFeatureValues::KBaseFeatureValuesClient::RpcClient->new,
	url => $url,
	headers => [],
    };

    chomp($self->{hostname} = `hostname`);
    $self->{hostname} ||= 'unknown-host';

    #
    # Set up for propagating KBRPC_TAG and KBRPC_METADATA environment variables through
    # to invoked services. If these values are not set, we create a new tag
    # and a metadata field with basic information about the invoking script.
    #
    if ($ENV{KBRPC_TAG})
    {
	$self->{kbrpc_tag} = $ENV{KBRPC_TAG};
    }
    else
    {
	my ($t, $us) = &$get_time();
	$us = sprintf("%06d", $us);
	my $ts = strftime("%Y-%m-%dT%H:%M:%S.${us}Z", gmtime $t);
	$self->{kbrpc_tag} = "C:$0:$self->{hostname}:$$:$ts";
    }
    push(@{$self->{headers}}, 'Kbrpc-Tag', $self->{kbrpc_tag});

    if ($ENV{KBRPC_METADATA})
    {
	$self->{kbrpc_metadata} = $ENV{KBRPC_METADATA};
	push(@{$self->{headers}}, 'Kbrpc-Metadata', $self->{kbrpc_metadata});
    }

    if ($ENV{KBRPC_ERROR_DEST})
    {
	$self->{kbrpc_error_dest} = $ENV{KBRPC_ERROR_DEST};
	push(@{$self->{headers}}, 'Kbrpc-Errordest', $self->{kbrpc_error_dest});
    }

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my $token = Bio::KBase::AuthToken->new(@args);
	
	if (!$token->error_message)
	{
	    $self->{token} = $token->token;
	    $self->{client}->{token} = $token->token;
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 estimate_k

  $obj->estimate_k($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.EstimateKParams
EstimateKParams is a reference to a hash where the following keys are defined:
	input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
	min_k has a value which is an int
	max_k has a value which is an int
	max_iter has a value which is an int
	random_seed has a value which is an int
	neighb_size has a value which is an int
	max_items has a value which is an int
	out_workspace has a value which is a string
	out_estimate_result has a value which is a string
ws_matrix_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.EstimateKParams
EstimateKParams is a reference to a hash where the following keys are defined:
	input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
	min_k has a value which is an int
	max_k has a value which is an int
	max_iter has a value which is an int
	random_seed has a value which is an int
	neighb_size has a value which is an int
	max_items has a value which is an int
	out_workspace has a value which is a string
	out_estimate_result has a value which is a string
ws_matrix_id is a string


=end text

=item Description

Used as an analysis step before generating clusters using K-means clustering, this method
provides an estimate of K by [...]

=back

=cut

 sub estimate_k
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function estimate_k (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to estimate_k:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'estimate_k');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.estimate_k",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'estimate_k',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method estimate_k",
					    status_line => $self->{client}->status_line,
					    method_name => 'estimate_k',
				       );
    }
}
 


=head2 estimate_k_new

  $obj->estimate_k_new($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.EstimateKParamsNew
EstimateKParamsNew is a reference to a hash where the following keys are defined:
	input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
	min_k has a value which is an int
	max_k has a value which is an int
	criterion has a value which is a string
	usepam has a value which is a KBaseFeatureValues.boolean
	alpha has a value which is a float
	diss has a value which is a KBaseFeatureValues.boolean
	random_seed has a value which is an int
	out_workspace has a value which is a string
	out_estimate_result has a value which is a string
ws_matrix_id is a string
boolean is an int

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.EstimateKParamsNew
EstimateKParamsNew is a reference to a hash where the following keys are defined:
	input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
	min_k has a value which is an int
	max_k has a value which is an int
	criterion has a value which is a string
	usepam has a value which is a KBaseFeatureValues.boolean
	alpha has a value which is a float
	diss has a value which is a KBaseFeatureValues.boolean
	random_seed has a value which is an int
	out_workspace has a value which is a string
	out_estimate_result has a value which is a string
ws_matrix_id is a string
boolean is an int


=end text

=item Description

Used as an analysis step before generating clusters using K-means clustering, this method
provides an estimate of K by [...]

=back

=cut

 sub estimate_k_new
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function estimate_k_new (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to estimate_k_new:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'estimate_k_new');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.estimate_k_new",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'estimate_k_new',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method estimate_k_new",
					    status_line => $self->{client}->status_line,
					    method_name => 'estimate_k_new',
				       );
    }
}
 


=head2 cluster_k_means

  $obj->cluster_k_means($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.ClusterKMeansParams
ClusterKMeansParams is a reference to a hash where the following keys are defined:
	k has a value which is an int
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	n_start has a value which is an int
	max_iter has a value which is an int
	random_seed has a value which is an int
	algorithm has a value which is a string
	out_workspace has a value which is a string
	out_clusterset_id has a value which is a string
ws_matrix_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.ClusterKMeansParams
ClusterKMeansParams is a reference to a hash where the following keys are defined:
	k has a value which is an int
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	n_start has a value which is an int
	max_iter has a value which is an int
	random_seed has a value which is an int
	algorithm has a value which is a string
	out_workspace has a value which is a string
	out_clusterset_id has a value which is a string
ws_matrix_id is a string


=end text

=item Description

Clusters features by K-means clustering.

=back

=cut

 sub cluster_k_means
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function cluster_k_means (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to cluster_k_means:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'cluster_k_means');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.cluster_k_means",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'cluster_k_means',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method cluster_k_means",
					    status_line => $self->{client}->status_line,
					    method_name => 'cluster_k_means',
				       );
    }
}
 


=head2 cluster_hierarchical

  $obj->cluster_hierarchical($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.ClusterHierarchicalParams
ClusterHierarchicalParams is a reference to a hash where the following keys are defined:
	distance_metric has a value which is a string
	linkage_criteria has a value which is a string
	feature_height_cutoff has a value which is a float
	condition_height_cutoff has a value which is a float
	max_items has a value which is an int
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	algorithm has a value which is a string
	out_workspace has a value which is a string
	out_clusterset_id has a value which is a string
ws_matrix_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.ClusterHierarchicalParams
ClusterHierarchicalParams is a reference to a hash where the following keys are defined:
	distance_metric has a value which is a string
	linkage_criteria has a value which is a string
	feature_height_cutoff has a value which is a float
	condition_height_cutoff has a value which is a float
	max_items has a value which is an int
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	algorithm has a value which is a string
	out_workspace has a value which is a string
	out_clusterset_id has a value which is a string
ws_matrix_id is a string


=end text

=item Description

Clusters features by hierarchical clustering.

=back

=cut

 sub cluster_hierarchical
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function cluster_hierarchical (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to cluster_hierarchical:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'cluster_hierarchical');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.cluster_hierarchical",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'cluster_hierarchical',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method cluster_hierarchical",
					    status_line => $self->{client}->status_line,
					    method_name => 'cluster_hierarchical',
				       );
    }
}
 


=head2 clusters_from_dendrogram

  $obj->clusters_from_dendrogram($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.ClustersFromDendrogramParams
ClustersFromDendrogramParams is a reference to a hash where the following keys are defined:
	feature_height_cutoff has a value which is a float
	condition_height_cutoff has a value which is a float
	input_data has a value which is a KBaseFeatureValues.ws_featureclusters_id
	out_workspace has a value which is a string
	out_clusterset_id has a value which is a string
ws_featureclusters_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.ClustersFromDendrogramParams
ClustersFromDendrogramParams is a reference to a hash where the following keys are defined:
	feature_height_cutoff has a value which is a float
	condition_height_cutoff has a value which is a float
	input_data has a value which is a KBaseFeatureValues.ws_featureclusters_id
	out_workspace has a value which is a string
	out_clusterset_id has a value which is a string
ws_featureclusters_id is a string


=end text

=item Description

Given a FeatureClusters with a dendogram built from a hierarchical clustering
method, this function creates new clusters by cutting the dendogram at
a specific hieght or by some other approach.

=back

=cut

 sub clusters_from_dendrogram
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function clusters_from_dendrogram (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to clusters_from_dendrogram:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'clusters_from_dendrogram');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.clusters_from_dendrogram",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'clusters_from_dendrogram',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method clusters_from_dendrogram",
					    status_line => $self->{client}->status_line,
					    method_name => 'clusters_from_dendrogram',
				       );
    }
}
 


=head2 evaluate_clusterset_quality

  $obj->evaluate_clusterset_quality($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.EvaluateClustersetQualityParams
EvaluateClustersetQualityParams is a reference to a hash where the following keys are defined:
	input_clusterset has a value which is a KBaseFeatureValues.ws_featureclusters_id
	out_workspace has a value which is a string
	out_report_id has a value which is a string
ws_featureclusters_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.EvaluateClustersetQualityParams
EvaluateClustersetQualityParams is a reference to a hash where the following keys are defined:
	input_clusterset has a value which is a KBaseFeatureValues.ws_featureclusters_id
	out_workspace has a value which is a string
	out_report_id has a value which is a string
ws_featureclusters_id is a string


=end text

=item Description

Given a FeatureClusters with a dendogram built from a hierarchical clustering
method, this function creates new clusters by cutting the dendogram at
a specific hieght or by some other approach.

=back

=cut

 sub evaluate_clusterset_quality
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function evaluate_clusterset_quality (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to evaluate_clusterset_quality:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'evaluate_clusterset_quality');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.evaluate_clusterset_quality",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'evaluate_clusterset_quality',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method evaluate_clusterset_quality",
					    status_line => $self->{client}->status_line,
					    method_name => 'evaluate_clusterset_quality',
				       );
    }
}
 


=head2 validate_matrix

  $obj->validate_matrix($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.ValidateMatrixParams
ValidateMatrixParams is a reference to a hash where the following keys are defined:
	method has a value which is a string
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
ws_matrix_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.ValidateMatrixParams
ValidateMatrixParams is a reference to a hash where the following keys are defined:
	method has a value which is a string
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
ws_matrix_id is a string


=end text

=item Description



=back

=cut

 sub validate_matrix
{
    my($self, @args) = @_;

# Authentication: optional

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function validate_matrix (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to validate_matrix:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'validate_matrix');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.validate_matrix",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'validate_matrix',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method validate_matrix",
					    status_line => $self->{client}->status_line,
					    method_name => 'validate_matrix',
				       );
    }
}
 


=head2 correct_matrix

  $obj->correct_matrix($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.CorrectMatrixParams
CorrectMatrixParams is a reference to a hash where the following keys are defined:
	transform_type has a value which is a string
	transform_value has a value which is a float
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	out_workspace has a value which is a string
	out_matrix_id has a value which is a string
ws_matrix_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.CorrectMatrixParams
CorrectMatrixParams is a reference to a hash where the following keys are defined:
	transform_type has a value which is a string
	transform_value has a value which is a float
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	out_workspace has a value which is a string
	out_matrix_id has a value which is a string
ws_matrix_id is a string


=end text

=item Description



=back

=cut

 sub correct_matrix
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function correct_matrix (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to correct_matrix:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'correct_matrix');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.correct_matrix",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'correct_matrix',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method correct_matrix",
					    status_line => $self->{client}->status_line,
					    method_name => 'correct_matrix',
				       );
    }
}
 


=head2 reconnect_matrix_to_genome

  $obj->reconnect_matrix_to_genome($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.ReconnectMatrixToGenomeParams
ReconnectMatrixToGenomeParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
	out_workspace has a value which is a string
	out_matrix_id has a value which is a string
ws_matrix_id is a string
ws_genome_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.ReconnectMatrixToGenomeParams
ReconnectMatrixToGenomeParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
	out_workspace has a value which is a string
	out_matrix_id has a value which is a string
ws_matrix_id is a string
ws_genome_id is a string


=end text

=item Description



=back

=cut

 sub reconnect_matrix_to_genome
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function reconnect_matrix_to_genome (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to reconnect_matrix_to_genome:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'reconnect_matrix_to_genome');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.reconnect_matrix_to_genome",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'reconnect_matrix_to_genome',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method reconnect_matrix_to_genome",
					    status_line => $self->{client}->status_line,
					    method_name => 'reconnect_matrix_to_genome',
				       );
    }
}
 


=head2 build_feature_set

  $obj->build_feature_set($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseFeatureValues.BuildFeatureSetParams
BuildFeatureSetParams is a reference to a hash where the following keys are defined:
	genome has a value which is a KBaseFeatureValues.ws_genome_id
	feature_ids has a value which is a string
	feature_ids_custom has a value which is a string
	base_feature_set has a value which is a KBaseFeatureValues.ws_featureset_id
	description has a value which is a string
	out_workspace has a value which is a string
	output_feature_set has a value which is a string
ws_genome_id is a string
ws_featureset_id is a string

</pre>

=end html

=begin text

$params is a KBaseFeatureValues.BuildFeatureSetParams
BuildFeatureSetParams is a reference to a hash where the following keys are defined:
	genome has a value which is a KBaseFeatureValues.ws_genome_id
	feature_ids has a value which is a string
	feature_ids_custom has a value which is a string
	base_feature_set has a value which is a KBaseFeatureValues.ws_featureset_id
	description has a value which is a string
	out_workspace has a value which is a string
	output_feature_set has a value which is a string
ws_genome_id is a string
ws_featureset_id is a string


=end text

=item Description



=back

=cut

 sub build_feature_set
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function build_feature_set (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to build_feature_set:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'build_feature_set');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.build_feature_set",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'build_feature_set',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method build_feature_set",
					    status_line => $self->{client}->status_line,
					    method_name => 'build_feature_set',
				       );
    }
}
 


=head2 get_matrix_descriptor

  $return = $obj->get_matrix_descriptor($GetMatrixDescriptorParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixDescriptorParams is a KBaseFeatureValues.GetMatrixDescriptorParams
$return is a KBaseFeatureValues.MatrixDescriptor
GetMatrixDescriptorParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
ws_matrix_id is a string
MatrixDescriptor is a reference to a hash where the following keys are defined:
	matrix_id has a value which is a string
	matrix_name has a value which is a string
	matrix_description has a value which is a string
	genome_id has a value which is a string
	genome_name has a value which is a string
	rows_count has a value which is an int
	columns_count has a value which is an int
	scale has a value which is a string
	type has a value which is a string
	row_normalization has a value which is a string
	col_normalization has a value which is a string

</pre>

=end html

=begin text

$GetMatrixDescriptorParams is a KBaseFeatureValues.GetMatrixDescriptorParams
$return is a KBaseFeatureValues.MatrixDescriptor
GetMatrixDescriptorParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
ws_matrix_id is a string
MatrixDescriptor is a reference to a hash where the following keys are defined:
	matrix_id has a value which is a string
	matrix_name has a value which is a string
	matrix_description has a value which is a string
	genome_id has a value which is a string
	genome_name has a value which is a string
	rows_count has a value which is an int
	columns_count has a value which is an int
	scale has a value which is a string
	type has a value which is a string
	row_normalization has a value which is a string
	col_normalization has a value which is a string


=end text

=item Description



=back

=cut

 sub get_matrix_descriptor
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_descriptor (received $n, expecting 1)");
    }
    {
	my($GetMatrixDescriptorParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixDescriptorParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixDescriptorParams\" (value was \"$GetMatrixDescriptorParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_descriptor:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_descriptor');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_descriptor",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_descriptor',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_descriptor",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_descriptor',
				       );
    }
}
 


=head2 get_matrix_row_descriptors

  $return = $obj->get_matrix_row_descriptors($GetMatrixItemDescriptorsParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixItemDescriptorsParams is a KBaseFeatureValues.GetMatrixItemDescriptorsParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
GetMatrixItemDescriptorsParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces has a value which is a reference to a list where each element is an int
	item_ids has a value which is a reference to a list where each element is a string
	requested_property_types has a value which is a reference to a list where each element is a string
ws_matrix_id is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string

</pre>

=end html

=begin text

$GetMatrixItemDescriptorsParams is a KBaseFeatureValues.GetMatrixItemDescriptorsParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
GetMatrixItemDescriptorsParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces has a value which is a reference to a list where each element is an int
	item_ids has a value which is a reference to a list where each element is a string
	requested_property_types has a value which is a reference to a list where each element is a string
ws_matrix_id is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string


=end text

=item Description



=back

=cut

 sub get_matrix_row_descriptors
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_row_descriptors (received $n, expecting 1)");
    }
    {
	my($GetMatrixItemDescriptorsParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixItemDescriptorsParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixItemDescriptorsParams\" (value was \"$GetMatrixItemDescriptorsParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_row_descriptors:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_row_descriptors');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_row_descriptors",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_row_descriptors',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_row_descriptors",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_row_descriptors',
				       );
    }
}
 


=head2 get_matrix_column_descriptors

  $return = $obj->get_matrix_column_descriptors($GetMatrixItemDescriptorsParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixItemDescriptorsParams is a KBaseFeatureValues.GetMatrixItemDescriptorsParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
GetMatrixItemDescriptorsParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces has a value which is a reference to a list where each element is an int
	item_ids has a value which is a reference to a list where each element is a string
	requested_property_types has a value which is a reference to a list where each element is a string
ws_matrix_id is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string

</pre>

=end html

=begin text

$GetMatrixItemDescriptorsParams is a KBaseFeatureValues.GetMatrixItemDescriptorsParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
GetMatrixItemDescriptorsParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces has a value which is a reference to a list where each element is an int
	item_ids has a value which is a reference to a list where each element is a string
	requested_property_types has a value which is a reference to a list where each element is a string
ws_matrix_id is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string


=end text

=item Description



=back

=cut

 sub get_matrix_column_descriptors
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_column_descriptors (received $n, expecting 1)");
    }
    {
	my($GetMatrixItemDescriptorsParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixItemDescriptorsParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixItemDescriptorsParams\" (value was \"$GetMatrixItemDescriptorsParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_column_descriptors:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_column_descriptors');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_column_descriptors",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_column_descriptors',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_column_descriptors",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_column_descriptors',
				       );
    }
}
 


=head2 get_matrix_rows_stat

  $return = $obj->get_matrix_rows_stat($GetMatrixItemsStatParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixItemsStatParams is a KBaseFeatureValues.GetMatrixItemsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemStat
GetMatrixItemsStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemStat is a reference to a hash where the following keys are defined:
	index_for has a value which is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avg has a value which is a float
	min has a value which is a float
	max has a value which is a float
	std has a value which is a float
	missing_values has a value which is an int

</pre>

=end html

=begin text

$GetMatrixItemsStatParams is a KBaseFeatureValues.GetMatrixItemsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemStat
GetMatrixItemsStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemStat is a reference to a hash where the following keys are defined:
	index_for has a value which is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avg has a value which is a float
	min has a value which is a float
	max has a value which is a float
	std has a value which is a float
	missing_values has a value which is an int


=end text

=item Description



=back

=cut

 sub get_matrix_rows_stat
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_rows_stat (received $n, expecting 1)");
    }
    {
	my($GetMatrixItemsStatParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixItemsStatParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixItemsStatParams\" (value was \"$GetMatrixItemsStatParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_rows_stat:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_rows_stat');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_rows_stat",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_rows_stat',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_rows_stat",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_rows_stat',
				       );
    }
}
 


=head2 get_matrix_columns_stat

  $return = $obj->get_matrix_columns_stat($GetMatrixItemsStatParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixItemsStatParams is a KBaseFeatureValues.GetMatrixItemsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemStat
GetMatrixItemsStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemStat is a reference to a hash where the following keys are defined:
	index_for has a value which is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avg has a value which is a float
	min has a value which is a float
	max has a value which is a float
	std has a value which is a float
	missing_values has a value which is an int

</pre>

=end html

=begin text

$GetMatrixItemsStatParams is a KBaseFeatureValues.GetMatrixItemsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemStat
GetMatrixItemsStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemStat is a reference to a hash where the following keys are defined:
	index_for has a value which is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avg has a value which is a float
	min has a value which is a float
	max has a value which is a float
	std has a value which is a float
	missing_values has a value which is an int


=end text

=item Description



=back

=cut

 sub get_matrix_columns_stat
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_columns_stat (received $n, expecting 1)");
    }
    {
	my($GetMatrixItemsStatParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixItemsStatParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixItemsStatParams\" (value was \"$GetMatrixItemsStatParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_columns_stat:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_columns_stat');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_columns_stat",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_columns_stat',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_columns_stat",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_columns_stat',
				       );
    }
}
 


=head2 get_matrix_row_sets_stat

  $return = $obj->get_matrix_row_sets_stat($GetMatrixSetsStatParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixSetsStatParams is a KBaseFeatureValues.GetMatrixSetsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemSetStat
GetMatrixSetsStatParams is a reference to a hash where the following keys are defined:
	params has a value which is a reference to a list where each element is a KBaseFeatureValues.GetMatrixSetStatParams
GetMatrixSetStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
	fl_indeces_for has a value which is a KBaseFeatureValues.boolean
	fl_avgs has a value which is a KBaseFeatureValues.boolean
	fl_mins has a value which is a KBaseFeatureValues.boolean
	fl_maxs has a value which is a KBaseFeatureValues.boolean
	fl_stds has a value which is a KBaseFeatureValues.boolean
	fl_missing_values has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemSetStat is a reference to a hash where the following keys are defined:
	indeces_for has a value which is a reference to a list where each element is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float
	missing_values has a value which is a reference to a list where each element is an int

</pre>

=end html

=begin text

$GetMatrixSetsStatParams is a KBaseFeatureValues.GetMatrixSetsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemSetStat
GetMatrixSetsStatParams is a reference to a hash where the following keys are defined:
	params has a value which is a reference to a list where each element is a KBaseFeatureValues.GetMatrixSetStatParams
GetMatrixSetStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
	fl_indeces_for has a value which is a KBaseFeatureValues.boolean
	fl_avgs has a value which is a KBaseFeatureValues.boolean
	fl_mins has a value which is a KBaseFeatureValues.boolean
	fl_maxs has a value which is a KBaseFeatureValues.boolean
	fl_stds has a value which is a KBaseFeatureValues.boolean
	fl_missing_values has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemSetStat is a reference to a hash where the following keys are defined:
	indeces_for has a value which is a reference to a list where each element is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float
	missing_values has a value which is a reference to a list where each element is an int


=end text

=item Description



=back

=cut

 sub get_matrix_row_sets_stat
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_row_sets_stat (received $n, expecting 1)");
    }
    {
	my($GetMatrixSetsStatParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixSetsStatParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixSetsStatParams\" (value was \"$GetMatrixSetsStatParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_row_sets_stat:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_row_sets_stat');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_row_sets_stat",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_row_sets_stat',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_row_sets_stat",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_row_sets_stat',
				       );
    }
}
 


=head2 get_matrix_column_sets_stat

  $return = $obj->get_matrix_column_sets_stat($GetMatrixSetsStatParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixSetsStatParams is a KBaseFeatureValues.GetMatrixSetsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemSetStat
GetMatrixSetsStatParams is a reference to a hash where the following keys are defined:
	params has a value which is a reference to a list where each element is a KBaseFeatureValues.GetMatrixSetStatParams
GetMatrixSetStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
	fl_indeces_for has a value which is a KBaseFeatureValues.boolean
	fl_avgs has a value which is a KBaseFeatureValues.boolean
	fl_mins has a value which is a KBaseFeatureValues.boolean
	fl_maxs has a value which is a KBaseFeatureValues.boolean
	fl_stds has a value which is a KBaseFeatureValues.boolean
	fl_missing_values has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemSetStat is a reference to a hash where the following keys are defined:
	indeces_for has a value which is a reference to a list where each element is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float
	missing_values has a value which is a reference to a list where each element is an int

</pre>

=end html

=begin text

$GetMatrixSetsStatParams is a KBaseFeatureValues.GetMatrixSetsStatParams
$return is a reference to a list where each element is a KBaseFeatureValues.ItemSetStat
GetMatrixSetsStatParams is a reference to a hash where the following keys are defined:
	params has a value which is a reference to a list where each element is a KBaseFeatureValues.GetMatrixSetStatParams
GetMatrixSetStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	item_indeces_for has a value which is a reference to a list where each element is an int
	item_indeces_on has a value which is a reference to a list where each element is an int
	fl_indeces_on has a value which is a KBaseFeatureValues.boolean
	fl_indeces_for has a value which is a KBaseFeatureValues.boolean
	fl_avgs has a value which is a KBaseFeatureValues.boolean
	fl_mins has a value which is a KBaseFeatureValues.boolean
	fl_maxs has a value which is a KBaseFeatureValues.boolean
	fl_stds has a value which is a KBaseFeatureValues.boolean
	fl_missing_values has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
ItemSetStat is a reference to a hash where the following keys are defined:
	indeces_for has a value which is a reference to a list where each element is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float
	missing_values has a value which is a reference to a list where each element is an int


=end text

=item Description



=back

=cut

 sub get_matrix_column_sets_stat
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_column_sets_stat (received $n, expecting 1)");
    }
    {
	my($GetMatrixSetsStatParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixSetsStatParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixSetsStatParams\" (value was \"$GetMatrixSetsStatParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_column_sets_stat:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_column_sets_stat');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_column_sets_stat",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_column_sets_stat',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_column_sets_stat",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_column_sets_stat',
				       );
    }
}
 


=head2 get_matrix_stat

  $return = $obj->get_matrix_stat($GetMatrixStatParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetMatrixStatParams is a KBaseFeatureValues.GetMatrixStatParams
$return is a KBaseFeatureValues.MatrixStat
GetMatrixStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
ws_matrix_id is a string
MatrixStat is a reference to a hash where the following keys are defined:
	mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
	row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	row_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat
	column_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat
MatrixDescriptor is a reference to a hash where the following keys are defined:
	matrix_id has a value which is a string
	matrix_name has a value which is a string
	matrix_description has a value which is a string
	genome_id has a value which is a string
	genome_name has a value which is a string
	rows_count has a value which is an int
	columns_count has a value which is an int
	scale has a value which is a string
	type has a value which is a string
	row_normalization has a value which is a string
	col_normalization has a value which is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string
ItemStat is a reference to a hash where the following keys are defined:
	index_for has a value which is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avg has a value which is a float
	min has a value which is a float
	max has a value which is a float
	std has a value which is a float
	missing_values has a value which is an int

</pre>

=end html

=begin text

$GetMatrixStatParams is a KBaseFeatureValues.GetMatrixStatParams
$return is a KBaseFeatureValues.MatrixStat
GetMatrixStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
ws_matrix_id is a string
MatrixStat is a reference to a hash where the following keys are defined:
	mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
	row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	row_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat
	column_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat
MatrixDescriptor is a reference to a hash where the following keys are defined:
	matrix_id has a value which is a string
	matrix_name has a value which is a string
	matrix_description has a value which is a string
	genome_id has a value which is a string
	genome_name has a value which is a string
	rows_count has a value which is an int
	columns_count has a value which is an int
	scale has a value which is a string
	type has a value which is a string
	row_normalization has a value which is a string
	col_normalization has a value which is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string
ItemStat is a reference to a hash where the following keys are defined:
	index_for has a value which is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avg has a value which is a float
	min has a value which is a float
	max has a value which is a float
	std has a value which is a float
	missing_values has a value which is an int


=end text

=item Description



=back

=cut

 sub get_matrix_stat
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_matrix_stat (received $n, expecting 1)");
    }
    {
	my($GetMatrixStatParams) = @args;

	my @_bad_arguments;
        (ref($GetMatrixStatParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetMatrixStatParams\" (value was \"$GetMatrixStatParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_matrix_stat:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_matrix_stat');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_matrix_stat",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_matrix_stat',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_matrix_stat",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_matrix_stat',
				       );
    }
}
 


=head2 get_submatrix_stat

  $return = $obj->get_submatrix_stat($GetSubmatrixStatParams)

=over 4

=item Parameter and return types

=begin html

<pre>
$GetSubmatrixStatParams is a KBaseFeatureValues.GetSubmatrixStatParams
$return is a KBaseFeatureValues.SubmatrixStat
GetSubmatrixStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	row_indeces has a value which is a reference to a list where each element is an int
	row_ids has a value which is a reference to a list where each element is a string
	column_indeces has a value which is a reference to a list where each element is an int
	column_ids has a value which is a reference to a list where each element is a string
	fl_row_set_stats has a value which is a KBaseFeatureValues.boolean
	fl_column_set_stat has a value which is a KBaseFeatureValues.boolean
	fl_mtx_row_set_stat has a value which is a KBaseFeatureValues.boolean
	fl_mtx_column_set_stat has a value which is a KBaseFeatureValues.boolean
	fl_row_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
	fl_column_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
	fl_values has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
SubmatrixStat is a reference to a hash where the following keys are defined:
	mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
	row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	row_set_stats has a value which is a KBaseFeatureValues.ItemSetStat
	column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
	mtx_row_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
	mtx_column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
	row_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
	column_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
	values has a value which is a reference to a list where each element is a reference to a list where each element is a float
MatrixDescriptor is a reference to a hash where the following keys are defined:
	matrix_id has a value which is a string
	matrix_name has a value which is a string
	matrix_description has a value which is a string
	genome_id has a value which is a string
	genome_name has a value which is a string
	rows_count has a value which is an int
	columns_count has a value which is an int
	scale has a value which is a string
	type has a value which is a string
	row_normalization has a value which is a string
	col_normalization has a value which is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string
ItemSetStat is a reference to a hash where the following keys are defined:
	indeces_for has a value which is a reference to a list where each element is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float
	missing_values has a value which is a reference to a list where each element is an int
PairwiseComparison is a reference to a hash where the following keys are defined:
	indeces has a value which is a reference to a list where each element is an int
	comparison_values has a value which is a reference to a list where each element is a reference to a list where each element is a float
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float

</pre>

=end html

=begin text

$GetSubmatrixStatParams is a KBaseFeatureValues.GetSubmatrixStatParams
$return is a KBaseFeatureValues.SubmatrixStat
GetSubmatrixStatParams is a reference to a hash where the following keys are defined:
	input_data has a value which is a KBaseFeatureValues.ws_matrix_id
	row_indeces has a value which is a reference to a list where each element is an int
	row_ids has a value which is a reference to a list where each element is a string
	column_indeces has a value which is a reference to a list where each element is an int
	column_ids has a value which is a reference to a list where each element is a string
	fl_row_set_stats has a value which is a KBaseFeatureValues.boolean
	fl_column_set_stat has a value which is a KBaseFeatureValues.boolean
	fl_mtx_row_set_stat has a value which is a KBaseFeatureValues.boolean
	fl_mtx_column_set_stat has a value which is a KBaseFeatureValues.boolean
	fl_row_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
	fl_column_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
	fl_values has a value which is a KBaseFeatureValues.boolean
ws_matrix_id is a string
boolean is an int
SubmatrixStat is a reference to a hash where the following keys are defined:
	mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
	row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
	row_set_stats has a value which is a KBaseFeatureValues.ItemSetStat
	column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
	mtx_row_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
	mtx_column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
	row_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
	column_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
	values has a value which is a reference to a list where each element is a reference to a list where each element is a float
MatrixDescriptor is a reference to a hash where the following keys are defined:
	matrix_id has a value which is a string
	matrix_name has a value which is a string
	matrix_description has a value which is a string
	genome_id has a value which is a string
	genome_name has a value which is a string
	rows_count has a value which is an int
	columns_count has a value which is an int
	scale has a value which is a string
	type has a value which is a string
	row_normalization has a value which is a string
	col_normalization has a value which is a string
ItemDescriptor is a reference to a hash where the following keys are defined:
	index has a value which is an int
	id has a value which is a string
	name has a value which is a string
	description has a value which is a string
	properties has a value which is a reference to a hash where the key is a string and the value is a string
ItemSetStat is a reference to a hash where the following keys are defined:
	indeces_for has a value which is a reference to a list where each element is an int
	indeces_on has a value which is a reference to a list where each element is an int
	size has a value which is an int
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float
	missing_values has a value which is a reference to a list where each element is an int
PairwiseComparison is a reference to a hash where the following keys are defined:
	indeces has a value which is a reference to a list where each element is an int
	comparison_values has a value which is a reference to a list where each element is a reference to a list where each element is a float
	avgs has a value which is a reference to a list where each element is a float
	mins has a value which is a reference to a list where each element is a float
	maxs has a value which is a reference to a list where each element is a float
	stds has a value which is a reference to a list where each element is a float


=end text

=item Description



=back

=cut

 sub get_submatrix_stat
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function get_submatrix_stat (received $n, expecting 1)");
    }
    {
	my($GetSubmatrixStatParams) = @args;

	my @_bad_arguments;
        (ref($GetSubmatrixStatParams) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"GetSubmatrixStatParams\" (value was \"$GetSubmatrixStatParams\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to get_submatrix_stat:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'get_submatrix_stat');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseFeatureValues.get_submatrix_stat",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'get_submatrix_stat',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method get_submatrix_stat",
					    status_line => $self->{client}->status_line,
					    method_name => 'get_submatrix_stat',
				       );
    }
}
 
  

sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
        method => "KBaseFeatureValues.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'get_submatrix_stat',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method get_submatrix_stat",
            status_line => $self->{client}->status_line,
            method_name => 'get_submatrix_stat',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for KBaseFeatureValues::KBaseFeatureValuesClient\n";
    }
    if ($sMajor == 0) {
        warn "KBaseFeatureValues::KBaseFeatureValuesClient version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 ws_genome_id

=over 4



=item Description

The workspace ID for a Genome data object.
@id ws KBaseGenomes.Genome


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 ws_conditionset_id

=over 4



=item Description

The workspace ID for a ConditionSet data object (Note: ConditionSet objects
do not yet exist - this is for now used as a placeholder).
@id ws KBaseExperiments.ConditionSet


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 FloatMatrix2D

=over 4



=item Description

A simple 2D matrix of floating point numbers with labels/ids for rows and
columns.  The matrix is stored as a list of lists, with the outer list
containing rows, and the inner lists containing values for each column of
that row.  Row/Col ids should be unique.

row_ids - unique ids for rows.
col_ids - unique ids for columns.
values - two dimensional array indexed as: values[row][col]
@metadata ws length(row_ids) as n_rows
@metadata ws length(col_ids) as n_cols


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
row_ids has a value which is a reference to a list where each element is a string
col_ids has a value which is a reference to a list where each element is a string
values has a value which is a reference to a list where each element is a reference to a list where each element is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
row_ids has a value which is a reference to a list where each element is a string
col_ids has a value which is a reference to a list where each element is a string
values has a value which is a reference to a list where each element is a reference to a list where each element is a float


=end text

=back



=head2 boolean

=over 4



=item Description

Indicates true or false values, false = 0, true = 1
@range [0,1]


=item Definition

=begin html

<pre>
an int
</pre>

=end html

=begin text

an int

=end text

=back



=head2 AnalysisReport

=over 4



=item Description

A basic report object used for a variety of cases to mark informational
messages, warnings, and errors related to processing or quality control
checks of raw data.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
checkTypeDetected has a value which is a string
checkUsed has a value which is a string
checkDescriptions has a value which is a reference to a list where each element is a string
checkResults has a value which is a reference to a list where each element is a KBaseFeatureValues.boolean
messages has a value which is a reference to a list where each element is a string
warnings has a value which is a reference to a list where each element is a string
errors has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
checkTypeDetected has a value which is a string
checkUsed has a value which is a string
checkDescriptions has a value which is a reference to a list where each element is a string
checkResults has a value which is a reference to a list where each element is a KBaseFeatureValues.boolean
messages has a value which is a reference to a list where each element is a string
warnings has a value which is a reference to a list where each element is a string
errors has a value which is a reference to a list where each element is a string


=end text

=back



=head2 ExpressionMatrix

=over 4



=item Description

A wrapper around a FloatMatrix2D designed for simple matricies of Expression
data.  Rows map to features, and columns map to conditions.  The data type 
includes some information about normalization factors and contains
mappings from row ids to features and col ids to conditions.

description - short optional description of the dataset
type - ? level, ratio, log-ratio
scale - ? probably: raw, ln, log2, log10
col_normalization - mean_center, median_center, mode_center, zscore
row_normalization - mean_center, median_center, mode_center, zscore
feature_mapping - map from row_id to feature id in the genome
data - contains values for (feature,condition) pairs, where 
    features correspond to rows and conditions are columns
    (ie data.values[feature][condition])

@optional description row_normalization col_normalization
@optional genome_ref feature_mapping conditionset_ref condition_mapping report

@metadata ws type
@metadata ws scale
@metadata ws row_normalization
@metadata ws col_normalization
@metadata ws genome_ref as Genome
@metadata ws conditionset_ref as ConditionSet
@metadata ws length(data.row_ids) as feature_count
@metadata ws length(data.col_ids) as condition_count


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
description has a value which is a string
type has a value which is a string
scale has a value which is a string
row_normalization has a value which is a string
col_normalization has a value which is a string
genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
feature_mapping has a value which is a reference to a hash where the key is a string and the value is a string
conditionset_ref has a value which is a KBaseFeatureValues.ws_conditionset_id
condition_mapping has a value which is a reference to a hash where the key is a string and the value is a string
data has a value which is a KBaseFeatureValues.FloatMatrix2D
report has a value which is a KBaseFeatureValues.AnalysisReport

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
description has a value which is a string
type has a value which is a string
scale has a value which is a string
row_normalization has a value which is a string
col_normalization has a value which is a string
genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
feature_mapping has a value which is a reference to a hash where the key is a string and the value is a string
conditionset_ref has a value which is a KBaseFeatureValues.ws_conditionset_id
condition_mapping has a value which is a reference to a hash where the key is a string and the value is a string
data has a value which is a KBaseFeatureValues.FloatMatrix2D
report has a value which is a KBaseFeatureValues.AnalysisReport


=end text

=back



=head2 SingleKnockoutFitnessMatrix

=over 4



=item Description

A wrapper around a FloatMatrix2D designed for simple matricies of Fitness data
for single gene/feature knockouts.  Generally fitness is measured as growth rate
for the knockout strain relative to wildtype.  This data type only supports
single feature knockouts.

description - short optional description of the dataset
type - ? level, ratio, log-ratio
scale - ? probably: raw, ln, log2, log10
col_normalization - mean_center, median_center, mode_center, zscore
row_normalization - mean_center, median_center, mode_center, zscore
feature_mapping - map from row_id to feature id in the genome
data - contains values for (feature,condition) pairs, where 
    features correspond to rows and conditions are columns
    (ie data.values[feature][condition])

@optional description row_normalization col_normalization
@optional genome_ref feature_ko_mapping conditionset_ref condition_mapping report

@metadata ws type
@metadata ws scale
@metadata ws row_normalization
@metadata ws col_normalization
@metadata ws genome_ref as Genome
@metadata ws length(data.row_ids) as feature_count
@metadata ws length(data.col_ids) as condition_count


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
description has a value which is a string
type has a value which is a string
scale has a value which is a string
row_normalization has a value which is a string
col_normalization has a value which is a string
genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
feature_ko_mapping has a value which is a reference to a hash where the key is a string and the value is a string
conditionset_ref has a value which is a KBaseFeatureValues.ws_conditionset_id
condition_mapping has a value which is a reference to a hash where the key is a string and the value is a string
data has a value which is a KBaseFeatureValues.FloatMatrix2D
report has a value which is a KBaseFeatureValues.AnalysisReport

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
description has a value which is a string
type has a value which is a string
scale has a value which is a string
row_normalization has a value which is a string
col_normalization has a value which is a string
genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
feature_ko_mapping has a value which is a reference to a hash where the key is a string and the value is a string
conditionset_ref has a value which is a KBaseFeatureValues.ws_conditionset_id
condition_mapping has a value which is a reference to a hash where the key is a string and the value is a string
data has a value which is a KBaseFeatureValues.FloatMatrix2D
report has a value which is a KBaseFeatureValues.AnalysisReport


=end text

=back



=head2 ws_matrix_id

=over 4



=item Description

A workspace ID that references a Float2DMatrix wrapper data object.
@id ws KBaseFeatureValues.ExpressionMatrix KBaseFeatureValues.SingleKnockoutFitnessMatrix


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 labeled_cluster

=over 4



=item Description

id_to_pos - simple representation of a cluster, which maps features/conditions of the cluster to the
row/col index in the data (0-based index).  The index is useful for fast lookup of data
for a specified feature/condition in the cluster.
@optional meancor msec


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id_to_pos has a value which is a reference to a hash where the key is a string and the value is an int
meancor has a value which is a float
msec has a value which is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id_to_pos has a value which is a reference to a hash where the key is a string and the value is an int
meancor has a value which is a float
msec has a value which is a float


=end text

=back



=head2 FeatureClusters

=over 4



=item Description

A set of clusters, typically generated for a Float2DMatrix wrapper, such as Expression
data or single feature knockout fitness data.

feature_clusters - list of labeled feature clusters
condition_clusters - (optional) list of labeled condition clusters
feature_dendrogram - (optional) maybe output from hierchical clustering approaches
condition_dendogram - (optional) maybe output from hierchical clustering approaches
original_data - pointer to the original data used to make this cluster set
report - information collected during cluster construction.

@metadata ws original_data as source_data_ref
@metadata ws length(feature_clusters) as n_feature_clusters
@metadata ws length(condition_clusters) as n_condition_clusters
@optional condition_clusters 
@optional feature_dendrogram condition_dendrogram
@optional original_data report


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
feature_clusters has a value which is a reference to a list where each element is a KBaseFeatureValues.labeled_cluster
condition_clusters has a value which is a reference to a list where each element is a KBaseFeatureValues.labeled_cluster
feature_dendrogram has a value which is a string
condition_dendrogram has a value which is a string
original_data has a value which is a KBaseFeatureValues.ws_matrix_id
report has a value which is a KBaseFeatureValues.AnalysisReport

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
feature_clusters has a value which is a reference to a list where each element is a KBaseFeatureValues.labeled_cluster
condition_clusters has a value which is a reference to a list where each element is a KBaseFeatureValues.labeled_cluster
feature_dendrogram has a value which is a string
condition_dendrogram has a value which is a string
original_data has a value which is a KBaseFeatureValues.ws_matrix_id
report has a value which is a KBaseFeatureValues.AnalysisReport


=end text

=back



=head2 ws_featureclusters_id

=over 4



=item Description

The workspace ID of a FeatureClusters data object.
@id ws KBaseFeatureValues.FeatureClusters


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 EstimateKResult

=over 4



=item Description

note: this needs review from Marcin


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
best_k has a value which is an int
estimate_cluster_sizes has a value which is a reference to a list where each element is a reference to a list containing 2 items:
	0: an int
	1: a float


</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
best_k has a value which is an int
estimate_cluster_sizes has a value which is a reference to a list where each element is a reference to a list containing 2 items:
	0: an int
	1: a float



=end text

=back



=head2 EstimateKParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
min_k has a value which is an int
max_k has a value which is an int
max_iter has a value which is an int
random_seed has a value which is an int
neighb_size has a value which is an int
max_items has a value which is an int
out_workspace has a value which is a string
out_estimate_result has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
min_k has a value which is an int
max_k has a value which is an int
max_iter has a value which is an int
random_seed has a value which is an int
neighb_size has a value which is an int
max_items has a value which is an int
out_workspace has a value which is a string
out_estimate_result has a value which is a string


=end text

=back



=head2 EstimateKParamsNew

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
min_k has a value which is an int
max_k has a value which is an int
criterion has a value which is a string
usepam has a value which is a KBaseFeatureValues.boolean
alpha has a value which is a float
diss has a value which is a KBaseFeatureValues.boolean
random_seed has a value which is an int
out_workspace has a value which is a string
out_estimate_result has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_matrix has a value which is a KBaseFeatureValues.ws_matrix_id
min_k has a value which is an int
max_k has a value which is an int
criterion has a value which is a string
usepam has a value which is a KBaseFeatureValues.boolean
alpha has a value which is a float
diss has a value which is a KBaseFeatureValues.boolean
random_seed has a value which is an int
out_workspace has a value which is a string
out_estimate_result has a value which is a string


=end text

=back



=head2 ClusterKMeansParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
k has a value which is an int
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
n_start has a value which is an int
max_iter has a value which is an int
random_seed has a value which is an int
algorithm has a value which is a string
out_workspace has a value which is a string
out_clusterset_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
k has a value which is an int
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
n_start has a value which is an int
max_iter has a value which is an int
random_seed has a value which is an int
algorithm has a value which is a string
out_workspace has a value which is a string
out_clusterset_id has a value which is a string


=end text

=back



=head2 ClusterHierarchicalParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
distance_metric has a value which is a string
linkage_criteria has a value which is a string
feature_height_cutoff has a value which is a float
condition_height_cutoff has a value which is a float
max_items has a value which is an int
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
algorithm has a value which is a string
out_workspace has a value which is a string
out_clusterset_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
distance_metric has a value which is a string
linkage_criteria has a value which is a string
feature_height_cutoff has a value which is a float
condition_height_cutoff has a value which is a float
max_items has a value which is an int
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
algorithm has a value which is a string
out_workspace has a value which is a string
out_clusterset_id has a value which is a string


=end text

=back



=head2 ClustersFromDendrogramParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
feature_height_cutoff has a value which is a float
condition_height_cutoff has a value which is a float
input_data has a value which is a KBaseFeatureValues.ws_featureclusters_id
out_workspace has a value which is a string
out_clusterset_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
feature_height_cutoff has a value which is a float
condition_height_cutoff has a value which is a float
input_data has a value which is a KBaseFeatureValues.ws_featureclusters_id
out_workspace has a value which is a string
out_clusterset_id has a value which is a string


=end text

=back



=head2 EvaluateClustersetQualityParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_clusterset has a value which is a KBaseFeatureValues.ws_featureclusters_id
out_workspace has a value which is a string
out_report_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_clusterset has a value which is a KBaseFeatureValues.ws_featureclusters_id
out_workspace has a value which is a string
out_report_id has a value which is a string


=end text

=back



=head2 ValidateMatrixParams

=over 4



=item Description

method - optional field specifying special type of validation
    necessary for particular clustering method.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
method has a value which is a string
input_data has a value which is a KBaseFeatureValues.ws_matrix_id

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
method has a value which is a string
input_data has a value which is a KBaseFeatureValues.ws_matrix_id


=end text

=back



=head2 CorrectMatrixParams

=over 4



=item Description

transform_type - type of matrix change (one of: add, multiply,
    normalize, missing, ?).
transform_value - optional field defining volume of change if
    it's necessary for chosen transform_type.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
transform_type has a value which is a string
transform_value has a value which is a float
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
out_workspace has a value which is a string
out_matrix_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
transform_type has a value which is a string
transform_value has a value which is a float
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
out_workspace has a value which is a string
out_matrix_id has a value which is a string


=end text

=back



=head2 ReconnectMatrixToGenomeParams

=over 4



=item Description

out_matrix_id - optional target matrix object name (if not specified 
    then target object overwrites input_data).


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
out_workspace has a value which is a string
out_matrix_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
genome_ref has a value which is a KBaseFeatureValues.ws_genome_id
out_workspace has a value which is a string
out_matrix_id has a value which is a string


=end text

=back



=head2 ws_featureset_id

=over 4



=item Description

The workspace ID of a FeatureSet data object.
@id ws KBaseCollections.FeatureSet


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 BuildFeatureSetParams

=over 4



=item Description

base_feature_set - optional field,
description - optional field.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
genome has a value which is a KBaseFeatureValues.ws_genome_id
feature_ids has a value which is a string
feature_ids_custom has a value which is a string
base_feature_set has a value which is a KBaseFeatureValues.ws_featureset_id
description has a value which is a string
out_workspace has a value which is a string
output_feature_set has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
genome has a value which is a KBaseFeatureValues.ws_genome_id
feature_ids has a value which is a string
feature_ids_custom has a value which is a string
base_feature_set has a value which is a KBaseFeatureValues.ws_featureset_id
description has a value which is a string
out_workspace has a value which is a string
output_feature_set has a value which is a string


=end text

=back



=head2 MatrixDescriptor

=over 4



=item Description

General info about matrix, including genome name that needs to be extracted from the genome object


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
matrix_id has a value which is a string
matrix_name has a value which is a string
matrix_description has a value which is a string
genome_id has a value which is a string
genome_name has a value which is a string
rows_count has a value which is an int
columns_count has a value which is an int
scale has a value which is a string
type has a value which is a string
row_normalization has a value which is a string
col_normalization has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
matrix_id has a value which is a string
matrix_name has a value which is a string
matrix_description has a value which is a string
genome_id has a value which is a string
genome_name has a value which is a string
rows_count has a value which is an int
columns_count has a value which is an int
scale has a value which is a string
type has a value which is a string
row_normalization has a value which is a string
col_normalization has a value which is a string


=end text

=back



=head2 ItemDescriptor

=over 4



=item Description

Basic information about a particular item in a collection. 

            index - index of the item
            id - id of the item
            name - name of the item
            description - description of the item                        
            properties - additinal proerties: key - property type, value - value. For instance, if item represents a feature, the property type can be a type of feature annotation in a genome, e.g. 'function', 'strand', etc


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
index has a value which is an int
id has a value which is a string
name has a value which is a string
description has a value which is a string
properties has a value which is a reference to a hash where the key is a string and the value is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
index has a value which is an int
id has a value which is a string
name has a value which is a string
description has a value which is a string
properties has a value which is a reference to a hash where the key is a string and the value is a string


=end text

=back



=head2 ItemStat

=over 4



=item Description

Statistics for a given item in a collection (defined by index) , calculated on the associated vector of values. 
Typical example is 2D matrix: item is a given row, and correposnding values from all columns is an associated vector.   
            
            In relation to ExpressionMatrix we can think about a gene (defined by row index in Float2DMatrix) and a vector of expression 
            values across all (or a subset of) conditions. In this case, index_for - index of a row representing a gene in the Float2DMatrix,  
            indeces_on - indeces of columns represnting a set of conditions on which we want to calculate statistics. 
             
            index_for - index of the item in a collection FOR which all statitics is collected
            indeces_on - indeces of items in the associated vector ON which the statistics is calculated
            size - number of elements in the associated vector
            avg - mean value for a given item across all elements in the associated vector 
            min - min value for a given item across all elements in the associated vector 
            max - max value for a given item across all elements in the associated vector 
            std - std value for a given item across all elements in the associated vector 
            missing_values - number of missing values for a given item across all elements in the associated vector


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
index_for has a value which is an int
indeces_on has a value which is a reference to a list where each element is an int
size has a value which is an int
avg has a value which is a float
min has a value which is a float
max has a value which is a float
std has a value which is a float
missing_values has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
index_for has a value which is an int
indeces_on has a value which is a reference to a list where each element is an int
size has a value which is an int
avg has a value which is a float
min has a value which is a float
max has a value which is a float
std has a value which is a float
missing_values has a value which is an int


=end text

=back



=head2 ItemSetStat

=over 4



=item Description

Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
2. No field names in json (avg, min, max, etc) for each element in the list


            indeces_for - indeces of items in a collection FOR which all statitics is collected
            indeces_on - indeces of items in the associated vector ON which the statistics is calculated
            size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
            avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
            mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
            maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
            stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
            missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
indeces_for has a value which is a reference to a list where each element is an int
indeces_on has a value which is a reference to a list where each element is an int
size has a value which is an int
avgs has a value which is a reference to a list where each element is a float
mins has a value which is a reference to a list where each element is a float
maxs has a value which is a reference to a list where each element is a float
stds has a value which is a reference to a list where each element is a float
missing_values has a value which is a reference to a list where each element is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
indeces_for has a value which is a reference to a list where each element is an int
indeces_on has a value which is a reference to a list where each element is an int
size has a value which is an int
avgs has a value which is a reference to a list where each element is a float
mins has a value which is a reference to a list where each element is a float
maxs has a value which is a reference to a list where each element is a float
stds has a value which is a reference to a list where each element is a float
missing_values has a value which is a reference to a list where each element is an int


=end text

=back



=head2 PairwiseComparison

=over 4



=item Description

To represent a pairwise comparison of several elements defined by 'indeces'.  
This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 

indeces - indeces of elements to be compared
comparison_values - values representing a parituclar type of comparison between elements. 
        Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
        Diagonal values: comparison_values[i][i] = 0
        
avgs - mean of comparison_values for each element        
mins - min of comparison_values for each element
maxs - max of comparison_values for each element
stds - std of comparison_values for each element


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
indeces has a value which is a reference to a list where each element is an int
comparison_values has a value which is a reference to a list where each element is a reference to a list where each element is a float
avgs has a value which is a reference to a list where each element is a float
mins has a value which is a reference to a list where each element is a float
maxs has a value which is a reference to a list where each element is a float
stds has a value which is a reference to a list where each element is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
indeces has a value which is a reference to a list where each element is an int
comparison_values has a value which is a reference to a list where each element is a reference to a list where each element is a float
avgs has a value which is a reference to a list where each element is a float
mins has a value which is a reference to a list where each element is a float
maxs has a value which is a reference to a list where each element is a float
stds has a value which is a reference to a list where each element is a float


=end text

=back



=head2 MatrixStat

=over 4



=item Description

Data type for bulk queries. It provides all necessary data to visulize basic properties of ExpressionMatrix 

mtx_descriptor - decriptor of the matrix as a whole
row_descriptors - descriptor for each row in the matrix (provides basic properties of the features)
column_descriptors - descriptor for each column in the matrix (provides basic properties of the conditions)
row_stats - basic statistics for each row (feature) in the matrix, like mean, min, max, etc acorss all columns (conditions)
column_stats - basic statistics for each row (feature) in the matrix, like mean, min, max, etc across all rows (features)


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
row_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat
column_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
row_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat
column_stats has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemStat


=end text

=back



=head2 SubmatrixStat

=over 4



=item Description

Data type for bulk queries. It provides various statistics calculated on sub-matrix. The sub-matrix is defined by a subset of rows and columns via parameters.
Parameters will also define the required types of statics.
                
mtx_descriptor - basic properties of the source matrix

row_descriptors - descriptor for each row in a subset defined in the parameters
column_descriptors - descriptor for each column in a subset defined in the parameters

row_set_stats - basic statistics for a subset of rows calculated on a subset of columns 
column_set_stat - basic statistics for a subset of columns calculated on a subset of rows

mtx_row_set_stat - basic statistics for a subset of rows calculated on ALL columns in the matrix (can be used as a backgound in comparison with row_set_stats)
mtx_column_set_stat - basic statistics for a subset of columns calculated on ALL rows in the matrix (can be used as a backgound in comparison with column_set_stat)

row_pairwise_correlation - pariwise perason correlation for a subset of rows (features)  
column_pairwise_correlation - pariwise perason correlation for a subset of columns (conditions)

values - sub-matrix representing actual values for a given subset of rows and a subset of columns


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
row_set_stats has a value which is a KBaseFeatureValues.ItemSetStat
column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
mtx_row_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
mtx_column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
row_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
column_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
values has a value which is a reference to a list where each element is a reference to a list where each element is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
mtx_descriptor has a value which is a KBaseFeatureValues.MatrixDescriptor
row_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
column_descriptors has a value which is a reference to a list where each element is a KBaseFeatureValues.ItemDescriptor
row_set_stats has a value which is a KBaseFeatureValues.ItemSetStat
column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
mtx_row_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
mtx_column_set_stat has a value which is a KBaseFeatureValues.ItemSetStat
row_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
column_pairwise_correlation has a value which is a KBaseFeatureValues.PairwiseComparison
values has a value which is a reference to a list where each element is a reference to a list where each element is a float


=end text

=back



=head2 GetMatrixDescriptorParams

=over 4



=item Description

Parameters to retrieve MatrixDescriptor


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id


=end text

=back



=head2 GetMatrixItemDescriptorsParams

=over 4



=item Description

Parameters to get basic properties for items from the Float2D type of matrices. 

input_data - worskapce reference to the ExpressionMatrix object (later we should allow to work with other Float2DMatrix-like matrices, e.g. fitness)
item_indeces - indeces of items for whch descriptors should be built. Either item_indeces or item_ids should be provided. If both are provided, item_indeces will be used.
item_ids - ids of items for whch descriptors should be built. Either item_indeces or item_ids should be provided. If both are provided, item_indeces will be used.
requested_property_types - list of property types to be populated for each item. Currently supported property types are: 'function'


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
item_indeces has a value which is a reference to a list where each element is an int
item_ids has a value which is a reference to a list where each element is a string
requested_property_types has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
item_indeces has a value which is a reference to a list where each element is an int
item_ids has a value which is a reference to a list where each element is a string
requested_property_types has a value which is a reference to a list where each element is a string


=end text

=back



=head2 GetMatrixItemsStatParams

=over 4



=item Description

Parameters to get statics for a set of items from the Float2D type of matrices. 

input_data - worskapce reference to the ExpressionMatrix object (later we should allow to work with other Float2DMatrix-like matrices, e.g. fitness)
item_indeces_for - indeces of items FOR whch statistics should be calculated 
item_indeces_on - indeces of items ON whch statistics should be calculated
fl_indeces_on - defines whether the indeces_on should be populated in ItemStat objects. The default value = 0.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
item_indeces_for has a value which is a reference to a list where each element is an int
item_indeces_on has a value which is a reference to a list where each element is an int
fl_indeces_on has a value which is a KBaseFeatureValues.boolean

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
item_indeces_for has a value which is a reference to a list where each element is an int
item_indeces_on has a value which is a reference to a list where each element is an int
fl_indeces_on has a value which is a KBaseFeatureValues.boolean


=end text

=back



=head2 GetMatrixSetStatParams

=over 4



=item Description

Parameters to get statistics for a set of items from the Float2D type of matrices in a form of ItemSetStat. 
This version is more flexible and will be later used to retrieve set of sets of elements.                  
            
            input_data - worskapce reference to the ExpressionMatrix object (later we should allow to work with other Float2DMatrix-like matrices, e.g. fitness)
            item_indeces_for - indeces of items FOR wich statistics should be calculated 
            item_indeces_on - indeces of items ON wich statistics should be calculated
            fl_indeces_on - defines whether the indeces_on should be populated in SetStat objects. The default value = 0. 
            fl_indeces_for - defines whether the indeces_for should be populated in SetStat objects. The default value = 0.             
            fl_avgs - defines whether the avgs should be populated in SetStat objects. The default value = 0. 
            fl_mins - defines whether the mins should be populated in SetStat objects. The default value = 0. 
            fl_maxs - defines whether the maxs should be populated in SetStat objects. The default value = 0. 
            fl_stds - defines whether the stds should be populated in SetStat objects. The default value = 0. 
            fl_missing_values - defines whether the missing_values should be populated in SetStat objects. The default value = 0.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
item_indeces_for has a value which is a reference to a list where each element is an int
item_indeces_on has a value which is a reference to a list where each element is an int
fl_indeces_on has a value which is a KBaseFeatureValues.boolean
fl_indeces_for has a value which is a KBaseFeatureValues.boolean
fl_avgs has a value which is a KBaseFeatureValues.boolean
fl_mins has a value which is a KBaseFeatureValues.boolean
fl_maxs has a value which is a KBaseFeatureValues.boolean
fl_stds has a value which is a KBaseFeatureValues.boolean
fl_missing_values has a value which is a KBaseFeatureValues.boolean

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
item_indeces_for has a value which is a reference to a list where each element is an int
item_indeces_on has a value which is a reference to a list where each element is an int
fl_indeces_on has a value which is a KBaseFeatureValues.boolean
fl_indeces_for has a value which is a KBaseFeatureValues.boolean
fl_avgs has a value which is a KBaseFeatureValues.boolean
fl_mins has a value which is a KBaseFeatureValues.boolean
fl_maxs has a value which is a KBaseFeatureValues.boolean
fl_stds has a value which is a KBaseFeatureValues.boolean
fl_missing_values has a value which is a KBaseFeatureValues.boolean


=end text

=back



=head2 GetMatrixSetsStatParams

=over 4



=item Description

Parameters to retrieve statistics for set of sets of elements. 

In relation to ExpressionMatrix, these parameters can be used to retrive sparklines for several gene clusters generated on the 
same ExpressionMatrix in one call.  

params - list of params to retrive statistics for a set of items from the Float2D type of matrices.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
params has a value which is a reference to a list where each element is a KBaseFeatureValues.GetMatrixSetStatParams

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
params has a value which is a reference to a list where each element is a KBaseFeatureValues.GetMatrixSetStatParams


=end text

=back



=head2 GetMatrixStatParams

=over 4



=item Description

Parameters to retrieve MatrixStat


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id


=end text

=back



=head2 GetSubmatrixStatParams

=over 4



=item Description

Parameters to retrieve SubmatrixStat        
input_data - reference to the source matrix        
        row_indeces - indeces defining a subset of matrix rows. Either row_indeces (highest priorery) or row_ids should be provided.
        row_ids - ids defining a subset of matrix rows. Either row_indeces (highest priorery) or row_ids should be provided.
        
        column_indeces - indeces defining a subset of matrix columns. Either column_indeces (highest priorery) or column_ids should be provided.
        column_ids - ids defining a subset of matrix columns. Either column_indeces (highest priorery) or column_ids should be provided.
        
        fl_row_set_stats - defines whether row_set_stats should be calculated in include in the SubmatrixStat. Default value = 0
        fl_column_set_stat - defines whether column_set_stat should be calculated in include in the SubmatrixStat. Default value = 0
        
fl_mtx_row_set_stat - defines whether mtx_row_set_stat should be calculated in include in the SubmatrixStat. Default value = 0
fl_mtx_column_set_stat - defines whether mtx_column_set_stat should be calculated in include in the SubmatrixStat. Default value = 0

fl_row_pairwise_correlation - defines whether row_pairwise_correlation should be calculated in include in the SubmatrixStat. Default value = 0        
fl_column_pairwise_correlation - defines whether column_pairwise_correlation should be calculated in include in the SubmatrixStat. Default value = 0
fl_values - defines whether values should be calculated in include in the SubmatrixStat. Default value = 0


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
row_indeces has a value which is a reference to a list where each element is an int
row_ids has a value which is a reference to a list where each element is a string
column_indeces has a value which is a reference to a list where each element is an int
column_ids has a value which is a reference to a list where each element is a string
fl_row_set_stats has a value which is a KBaseFeatureValues.boolean
fl_column_set_stat has a value which is a KBaseFeatureValues.boolean
fl_mtx_row_set_stat has a value which is a KBaseFeatureValues.boolean
fl_mtx_column_set_stat has a value which is a KBaseFeatureValues.boolean
fl_row_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
fl_column_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
fl_values has a value which is a KBaseFeatureValues.boolean

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
input_data has a value which is a KBaseFeatureValues.ws_matrix_id
row_indeces has a value which is a reference to a list where each element is an int
row_ids has a value which is a reference to a list where each element is a string
column_indeces has a value which is a reference to a list where each element is an int
column_ids has a value which is a reference to a list where each element is a string
fl_row_set_stats has a value which is a KBaseFeatureValues.boolean
fl_column_set_stat has a value which is a KBaseFeatureValues.boolean
fl_mtx_row_set_stat has a value which is a KBaseFeatureValues.boolean
fl_mtx_column_set_stat has a value which is a KBaseFeatureValues.boolean
fl_row_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
fl_column_pairwise_correlation has a value which is a KBaseFeatureValues.boolean
fl_values has a value which is a KBaseFeatureValues.boolean


=end text

=back



=cut

package KBaseFeatureValues::KBaseFeatureValuesClient::RpcClient;
use base 'JSON::RPC::Client';
use POSIX;
use strict;

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $headers, $obj) = @_;
    my $result;


    {
	if ($uri =~ /\?/) {
	    $result = $self->_get($uri);
	}
	else {
	    Carp::croak "not hashref." unless (ref $obj eq 'HASH');
	    $result = $self->_post($uri, $headers, $obj);
	}

    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $headers, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	@$headers,
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
